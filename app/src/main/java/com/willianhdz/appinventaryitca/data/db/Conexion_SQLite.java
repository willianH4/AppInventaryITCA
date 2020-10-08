package com.willianhdz.appinventaryitca.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.willianhdz.appinventaryitca.data.Dtos.producto;
import com.willianhdz.appinventaryitca.data.Dtos.usuario;
import com.willianhdz.appinventaryitca.data.Dtos.Categorias2;
import com.willianhdz.appinventaryitca.R;
//import com.willianhdz.appinventaryitca.RecyclerView.AdaptadorUsuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Conexion_SQLite extends SQLiteOpenHelper {


    boolean estado = true;
    boolean estadoDelete = true;
    //Instancias para futuros metodos
    ArrayList<String> listaArticulos;

    private ArrayList<usuario> usuariosList;
    private ArrayList<Categorias2> categoriasList;
    private ArrayList<String> listaCategorias;
    private ArrayList<producto> productoList;

    public Conexion_SQLite(Context context){ super(context, "WCABN.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREACION DE LA TABLA tb_categoria
        db.execSQL("create table tb_categoria(id_categoria integer primary key autoincrement ," +
                " nom_categoria varchar(50) not null, estado_categoria int(1) not null)");

        //CREACION DE LA PRIMERA TABLA tb_producto
        db.execSQL("create table tb_producto(id_producto integer primary key autoincrement ," +
                " nombre_producto varchar(50) not null, des_producto varchar(90) not null, stock real not null , precio real not null, " +
                " unidad_de_medida varchar(20) not null, estado_producto int(1) not null, fecha_entrada datetime not null, " +
                " categoria int(5) not null, " +
                "FOREIGN KEY (categoria) REFERENCES tb_categoria(id_categoria))");

        //CREACION DE LA TABLA tb_usuario
        db.execSQL("create table tb_usuario(id_usuario interger not null primary key unique, nombre varchar(60) not null," +
                " apellido varchar(30) not null, correo varchar(45) not null, usuario varchar(30) not null," +
                " clave varchar(150) not null, tipo int(1) not null, estado int(1) not null, pregunta varchar(60) not null," +
                " respuesta varchar(35), fecha_de_registro datetime not null )");

        //SE CREO UN  REGISTRO DE USUARIO
        //PARA ASI PUEDA INICIAR SESION SIN REGISTRARSE ES EL USUARIO MASTER
        db.execSQL("insert into tb_usuario values('1', 'Cristian','Orellana','prueba@gmail.com','Administrador'," +
                " '123','1', '1', '¿Nombre de tu abuela?', 'Emelina', datetime('now', 'localtime'))");

        db.execSQL("insert into tb_categoria values('1','Electronica','1')");
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists tb_producto");
        db.execSQL("drop table if exists tb_categoria");
        db.execSQL("drop table if exists tb_usuario");
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    Calendar cal = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha1 = sdf.format(cal.getTime());


    public SQLiteDatabase bd() {
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd;
    }


    //METODOS PARA LA TABLA UUARIO

    //Funcion para insertar registros
    public boolean InsertarUsuario(usuario us) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            registro.put("id_usuario", us.getId_usu());
            registro.put("nombre", us.getNombre());
            registro.put("apellido", us.getApellido());
            registro.put("correo", us.getCorreo());
            registro.put("usuario", us.getUsuario());
            registro.put("clave", us.getClave());
            registro.put("tipo", us.getTipo());
            registro.put("estado", us.getEstado_us());
            registro.put("pregunta", us.getPregunta());
            registro.put("respuesta", us.getRespuesta());
            registro.put("fecha_de_registro", getDateTime());

            Cursor fila = bd().rawQuery("select id_usuario from tb_usuario where id_usuario=" + us.getId_usu() + "", null);
            if (fila.moveToFirst() == true){
                estado = false;
            }else {
                resultado = (int)bd().insert("tb_usuario", null, registro);
                if (resultado > 0){
                    estado = true;
                }else {
                    estado = false;
                }
            }
        }catch (Exception ex){
            estado = false;
            Log.e("Error: ", ex.toString());
        }
        return estado;
    }

    //Funcion para consultar un registro
    public boolean consultaidUsuario(usuario us) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getReadableDatabase();
        try {
            String[] parametros = {String.valueOf(us.getId_usu())};
            String[] campos = {
                    "id_usuario", "nombre", "apellido", "correo",
                    "usuario", "clave", "tipo", "estado", "pregunta",
                    "respuesta", "fecha_de_registro"
            };
            Cursor fila = bd.query("tb_usuario", campos, "id_usuario=?", parametros, null, null, null);
            if (fila.moveToFirst()){
                us.setId_usu(Integer.parseInt(fila.getString(0)));
                us.setNombre(fila.getString(1));
                us.setApellido(fila.getString(2));
                us.setCorreo(fila.getString(3));
                us.setUsuario(fila.getString(4));
                us.setClave(fila.getString(5));
                us.setTipo(Integer.parseInt(fila.getString(6)));
                us.setEstado_us(Integer.parseInt(fila.getString(7)));
                us.setPregunta(fila.getString(8));
                us.setRespuesta(fila.getString(9));
                estado = true;

            }else {
                estado = false;
            }
            fila.close();
            bd.close();
        }catch (Exception ex){
            estado = false;
            Log.e("Error.", ex.toString());
        }
        return estado;

    }

    //Funcion para eliminar un registro
    public boolean bajaidUsuario(final Context context, final usuario us) {
        estado = true;
        try {
            int codigo = us.getId_usu();
            Cursor fila = bd().rawQuery("select * from tb_usuario where id_usuario=" + codigo + "", null);
            if (fila.moveToFirst()){
                us.setId_usu(Integer.parseInt(fila.getString(0)));
                us.setNombre(fila.getString(1));
                us.setApellido(fila.getString(2));
                us.setCorreo(fila.getString(3));
                us.setUsuario(fila.getString(4));
                us.setClave(fila.getString(5));
                us.setTipo(Integer.parseInt(fila.getString(6)));
                us.setEstado_us(Integer.parseInt(fila.getString(7)));
                us.setPregunta(fila.getString(8));
                us.setRespuesta(fila.getString(9));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setMessage("¿Estas seguro de borrar el registro?");
                builder.setCancelable(false);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = us.getId_usu();
                        int cant = bd().delete("tb_usuario", "id_usuario=" + id, null);
                        if (cant > 0){
                            estado = true;
                            Toast.makeText(context, "Registro eliminado sastifactoriamente", Toast.LENGTH_SHORT).show();
                        }else {
                            estado = false;
                        }
                        bd().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }else {
                Toast.makeText(context, "No hay resultados encontrados", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            estado = false;
            Log.e("Error.", ex.toString());
        }
        return estado;
    }

    //Funcion para insertar registros
    public boolean modificarUsuario(usuario us){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{

            int id =  us.getId_usu();
            String nom = us.getNombre();
            String ape = us.getApellido();
            String email = us.getCorreo();
            String user = us.getUsuario();
            String pass = us.getClave();
            int tipo = us.getTipo();
            int est = us.getEstado_us();
            String preg = us.getPregunta();
            String res = us.getRespuesta();

            ContentValues registro = new ContentValues();
            registro.put("id_usuario", id);
            registro.put("nombre", nom);
            registro.put("apellido", ape);
            registro.put("correo", email);
            registro.put("usuario", user);
            registro.put("clave", pass);
            registro.put("tipo", tipo);
            registro.put("estado", est);
            registro.put("pregunta", preg);
            registro.put("respuesta", res);
            registro.put("fecha_de_registro", getDateTime());

            int cant = (int) bd.update("tb_usuario", registro, "id_usuario=" + id, null);
            bd.close();
            if(cant>0) {
                estado = true;
            }else {
                estado = false;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    public List<usuario> mostrarUsuario(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM tb_usuario order by id_usuario desc", null);
        List<usuario> usuarios = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                usuarios.add(new usuario(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6),
                        cursor.getInt(7), cursor.getString(8), cursor.getString(9)));
            }while (cursor.moveToNext());
        }
        return usuarios;
    }


    public Cursor consultarusu(String usu, String pas) throws SQLException {
        Cursor mcursor = null;

        mcursor =this.getReadableDatabase().query("tb_usuario", new String[]{"id_usuario", "nombre",
                "apellido","correo, usuario", "clave", "tipo", "estado", "pregunta", "respuesta"}, "correo like'"+usu+"' "+"and clave like'"+pas+"'",null,null,null,null);

        return  mcursor;
    }
/******************************************************************************************************/
/******************************************************************************************************/
    /************************************************TABLA CATEGORIA********************************/

    public boolean InserCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        //SQLiteDatabase bd —— this.getWritableDatabase(),
        try {
            int id_categoria = datos.getId_categoria();
            String nom_categoria = datos.getNombre();
            int estadoCategoria = datos.getEstado();

            //Cursor fila —— this.getWritableDatabase().rawQuery(”select codigo from adiculos where codigo——”’+codigo+ ’, null),
            Cursor fila = bd().rawQuery("select id_categoria from tb_categoria where id_categoria='" + id_categoria + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                //estado —— (boolean)this.getWritableDatabase().insed(”datos”,”nombre, correo, telefono”, registro),
                //resultado —— (int) this.getWritableDatabase().insert(”usuarios”, "nombres, apellidos, usuario, clave,pregunta,respuesta", registro),
                String SQL = "INSERT INTO tb_categoria \n" + "(id_categoria,nom_categoria,estado_categoria)\n" + "VALUES \n" +
                        "('" + id_categoria + "', '" + nom_categoria + "', '" + estadoCategoria + "');";
                bd().execSQL(SQL);
                bd().close();

        /*
        this.getWritableDatabase().execSQL(SQL):
        this.getWritabIeDatabase(). close();

        */

                estado = true;
            }

        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }



    public boolean insertardatosCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            //registro.put(”codigo",datos.getCodigo());
            registro.put("id_categoria", datos.getId_categoria());
            registro.put("nom_categoria", datos.getNombre());
            registro.put("estado_categoria", datos.getEstado());
            /*adicione estas lineas para verificar sino existe un usuario con el mismo correo INICIO. */
            //Cursor fila —— this.getWritableDatabase().rawQuery(”select codigo from adiculos where codigo——”’+datos.getCodigo()+”’”, null),
            Cursor fila = bd().rawQuery("select id_categoria from tb_categoria where id_categoria='" + datos.getId_categoria() + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                /*FIN*/
                //estado —— (boolean)this.getWritableDatabase().insed(”datos”,”nombre, correo, telefono”,registro),
                //resultado —— (int) this.getWritableDatabase().insed(”adiculos”, "codigo, descripcion,precio", registro),"
                resultado = (int) bd().insert("tb_categoria", null, registro);
                //resu/tado —— (int) this.getWritableDatabase().insert(”articulos", "codigo", registro),
                if (resultado > 0) estado = true;
                else estado = false;

            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());

        }
        return estado;
    }


    public boolean InsertRegisterCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        try {

            int id_categoria = datos.getId_categoria();
            String nom_categoria = datos.getNombre();
            int estado_categoria = datos.getEstado();


            Cursor fila = bd().rawQuery("select id_categoria from tb_categoria where id_categoria='" +
                    datos.getId_categoria() + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;

            } else {
                //estado —— (boolean)this.getWritableDatabase().insed(”datos”,”nombre, correo, telefono”, registro),
                //resultado —— (int) this.getWritableDatabase().insert(”usuarios”, "nombres, apellidos, usuario, clave,pregunta,respuesta", registro),
                String SQL = "INSERT INTO tb_categoria \n" + "(id_categoria,nom_categoria,estado_categoria)\n" + "VALUES\n" + "(?,?,?);";

                //resultado —— (int) this.getWritableDatabase().insed(”usuarios”, nombres, apellidos, usuario, clave,pregunta,respuesta”, registro),
                bd().execSQL(SQL, new String[]{String.valueOf(id_categoria), nom_categoria, String.valueOf(estado_categoria)});
                //if (resultado > 0) estado —— true,’
                //else estado —— false,
                estado = true;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultaIdCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {

            int id_categoria = datos.getId_categoria();
            //Cursor fila —— bd.rawQuery(”select descripcion, precio from articulos where codigo——”’+ codigo +	', null),’
            //Cursor fila —— bd.rawQuery(”select descripcion, precio from articulos where codigo——” + codigo, null),”
            Cursor fila = bd.rawQuery("select id_categoria, nom_categoria, estado_categoria from tb_categoria where id_categoria=" + id_categoria, null);
            if (fila.moveToFirst()) {
                datos.setId_categoria(Integer.parseInt(fila.getString(0)));
                datos.setNombre(fila.getString(1));
                datos.setEstado(Integer.parseInt(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }
    //Otra forma de realizar una consulta. método opcional de realizar.

    public boolean consultaCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;

        //SQLiteDatabase bd —— this.getWritableDatabase(),
        SQLiteDatabase bd = this.getReadableDatabase();
        try {
            String[] parametros = {String.valueOf(datos.getId_categoria())};
            String[] campos = {"id_categoria", "nom_categoria", "estado_categoria"};
            Cursor fila = bd.query("tb_categoria", campos, "id_categoria=?", parametros, null, null, null);
            //fila. moveToFirst(),
            if (fila.moveToFirst()) {
                datos.setId_categoria(Integer.parseInt(fila.getString(0)));
                datos.setNombre(fila.getString(1));
                datos.setEstado(Integer.parseInt(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            fila.close();
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }
    //Fin del método opcional


    public boolean consultarNombreCategoria(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String nombre = datos.getNombre();
            Cursor fila = bd.rawQuery("select id_categoria, nom_categoria, estado_categoria from tb_categoria " +
                    "where nom_categoria=" + nombre + "'", null);
            if (fila.moveToFirst()) {
                datos.setId_categoria(Integer.parseInt(fila.getString(0)));
                datos.setNombre(fila.getString(1));
                datos.setEstado(Integer.parseInt(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean bajaIdCategorias(final Context context, final Categorias2 datos) {
        //SQLiteDatabase bd —— this.getWritableDatabase(),
        final boolean[] estadoDelete = {true};
        try {
            int id = datos.getId_categoria();
            Cursor fila = bd().rawQuery("select * from tb_categoria where id_categoria=" + id, null);
            if (fila.moveToFirst()) {
                datos.setId_categoria(Integer.parseInt(fila.getString(0)));
                datos.setNombre(fila.getString(1));
                datos.setEstado(Integer.parseInt(fila.getString(2)));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_borrar);
                builder.setTitle("Warning");
                builder.setMessage("¿Esta seguro de borrar el registro? \nid_categoria: " + datos.getId_categoria() + "\nNombre: " + datos.getNombre());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String[] parametros —— {String. valueOf(datos.getCodigo())},
                        int id = datos.getId_categoria();
                        int cant = bd().delete("tb_categoria", "id_categoria=" + id, null);
                        //bd(). delete(”articulos”,”codigo——?”,parametros),
                        if (cant > 0) {
                            estadoDelete[0] = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            estadoDelete[0] = false;
                        }
                        bd().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay resultados encontrados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            estadoDelete[0] = false;
            Log.e("Error.", e.toString());
        }
        return estadoDelete[0];
    }

    public boolean modificarCategorias(Categorias2 datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            int id = datos.getId_categoria();
            String nombre = datos.getNombre();
            int estado_categoria= datos.getEstado();

            //String[j parametros —— {String. vaIueOf(datos.getCodigo())},

            ContentValues registro = new ContentValues();
            registro.put("id_categoria", id);
            registro.put("nom_categoria", nombre);
            registro.put("estado_categoria", estado_categoria);
            //int cant —— (int) this.getWritableDatabase().update(”adiculos”, registro, "codigo——" + codigo, null),”
            int cant = (int) bd.update("tb_categoria", registro, "id_categoria=" + id, null);
            //bd.update("articulos”,registro,"codigo——?”,parametros),’
            bd.close();
            if (cant > 0) estado = true;
            else estado = false;
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    //lnicio del Método para crear lista de datos de la BD en el Spinner.
    //public void consultaListaAdiculos(){
    public ArrayList<Categorias2> consultaListaCategorias() {
        boolean estado = false;
        //SQLiteDatabase bd —— this.getWritableDatabase(),
        SQLiteDatabase bd = this.getReadableDatabase();
        Categorias2 categoria = null;    //Creamos la instancia vacia.
        ArrayList<Categorias2> categoriasList = new ArrayList<Categorias2>();

        try {

            Cursor fila = bd.rawQuery("select * from tb_categoria", null);
            while (fila.moveToNext()) {
                categoria = new Categorias2();
                categoria.setId_categoria(fila.getInt(0));
                categoria.setNombre(fila.getString(1));
                categoria.setEstado(fila.getInt(2));

                categoriasList.add(categoria);

                Log.i("id_categoria", String.valueOf(categoria.getId_categoria()));
                Log.i("nom_categoria", categoria.getNombre().toString());
                Log.i("estado_categoria", String.valueOf(categoria.getEstado()));
            }

            obtenerListaCategorias();

        } catch (Exception e) {

        }
        return categoriasList;
    }

    public ArrayList<String> obtenerListaCategorias() {
        ArrayList<String> listaCategorias = new ArrayList<String>();
        //IistaArticulos —— new ArrayList<>(),
        listaCategorias.add("Seleccione");

        for (int i = 0; i < categoriasList.size(); i++) {
            //IistaArticulos.add(String. valueOf(articulosList.get(i).getCodigo())),
            listaCategorias.add(categoriasList.get(i).getId_categoria() + " ~ " + categoriasList.get(i).getNombre());
        }
        //bd(). close(),
        return listaCategorias;
    }
    //Fin del Spinner.


    //Inicio del Método para crear lista de datos de la BD en el ListView.

    public ArrayList<String> consultaListaCategorias1() {
        boolean estado = false;
        //SQLiteDatabase bd —— this.getWritableDatabase(),
        SQLiteDatabase bd = this.getReadableDatabase();

        Categorias2 categoria = null;    //Creamos la instancia vacia.
        ArrayList<Categorias2> categoriasList = new ArrayList<Categorias2>();

        try {
            Cursor fila = bd.rawQuery("select * from tb_categoria", null);
            while (fila.moveToNext()) {
                categoria = new Categorias2();
                categoria.setId_categoria(fila.getInt(0));
                categoria.setNombre(fila.getString(1));
                categoria.setEstado(fila.getInt(2));

                categoriasList.add(categoria);
            }

            listaCategorias = new ArrayList<String>();
            //IistaArticulos —— new ArrayList<>(),
            //IistaArticulos.add(”Seleccione"),

            for (int i = 0; i <= categoriasList.size(); i++) {
                //IistaArticulos. add(String. valueOf(adiculosList.get(i).getCodigo())),
                listaCategorias.add(categoriasList.get(i).getId_categoria() + " ~ " + categoriasList.get(i).getNombre());
            }
            //bd(). close(),
            //return IistaArticulos,’

        } catch (Exception e) {

        }
        //return articulosList,”
        return listaCategorias;

    }

    public List<Categorias2> mostrarCategorias(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM tb_categoria order by id_categoria desc", null);
        List<Categorias2> categorias = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                categorias.add(new Categorias2(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return categorias;
    }

/******************************************************************************************************/
/******************************************************************************************************/
    /************************************************TABLA CATEGORIA********************************/


    //Funcion para insertar registros
    public boolean InsertarProducto(producto po) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            registro.put("id_producto", po.getId_pr());
            registro.put("nombre_producto", po.getNom_pro());
            registro.put("des_producto", po.getDes_pro());
            registro.put("stock", po.getStock());
            registro.put("precio", po.getPrecio());
            registro.put("unidad_de_medida", po.getUnidad());
            registro.put("estado_producto", po.getEstado());
            registro.put("categoria", po.getCategoria());
            registro.put("fecha_entrada", getDateTime());


            Cursor fila = bd().rawQuery("select id_producto from tb_producto where id_producto=" + po.getId_pr(), null);
            if (fila.moveToFirst() == true){
                estado = false;
            }else {
                resultado = (int)bd().insert("tb_producto", null, registro);
                if (resultado > 0){
                    estado = true;
                }else {
                    estado = false;
                }
            }
        }catch (Exception ex){
            estado = false;
            Log.e("Error: ", ex.toString());
        }
        return estado;
    }
    /*
    //METODOS PARA LA TABLA Produsto

    public boolean insertarProducto(producto datos){
        ContentValues productoValues = new ContentValues();
        boolean estado = false;
        try{
            productoValues.put("id_producto", datos.getId_pr());
            productoValues.put("nombre_producto", datos.getNom_pro());
            productoValues.put("des_producto", datos.getDes_pro());
            productoValues.put("stock", datos.getStock());
            productoValues.put("precio", datos.getPrecio());
            productoValues.put("unidad_de_medida", datos.getUnidad());
            productoValues.put("estado_producto", datos.getEstado());
            productoValues.put("fecha_entrada", this.getDateTime());
            productoValues.put("categoria",datos.getCategoria());

            SQLiteDatabase gestor = bd();
            if(gestor.insert("tb_producto", null,productoValues) < 0){
                Log.i("insertar ", "Error de insercion");
                estado = false;
            }
            estado = true;
        }catch (Exception ex){
            Log.i("Error insertar ", ex.toString());
            estado = false;
        }
        return estado;
    }
*/
    public boolean consultarNombreProducto(producto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String nombre = datos.getNom_pro();
            Cursor fila = bd.rawQuery("select id_producto, nombre_producto, des_producto, stock, precio, unidad_de_medida, estado_producto from tb_producto " +
                    "where nombre_producto='" + nombre + "'", null);
            if (fila.moveToFirst()) {
                datos.setId_pr(Integer.parseInt(fila.getString(0)));
                datos.setNom_pro(fila.getString(1));
                datos.setDes_pro(fila.getString(2));
                datos.setStock(Double.parseDouble(fila.getString(3)));
                datos.setPrecio(Double.parseDouble(fila.getString(4)));
                datos.setUnidad(fila.getString(5));


                estado = true;
            } else {
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultarIdProducto(producto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            int Id = datos.getId_pr();
            Cursor fila = bd.rawQuery("select id_producto, nombre_producto, des_producto, stock, precio, unidad_de_medida from tb_producto where id_producto="+ Id, null);
            if (fila.moveToFirst()) {
                datos.setId_pr(Integer.parseInt(fila.getString(0)));
                datos.setNom_pro(fila.getString(1));
                datos.setDes_pro(fila.getString(2));
                datos.setStock(Double.parseDouble(fila.getString(3)));
                datos.setPrecio(Double.parseDouble(fila.getString(4)));
                datos.setUnidad(fila.getString(5));


                estado = true;
            } else {
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public ArrayList<producto> listarProducto(){
        ArrayList<producto> productosList = new ArrayList<producto>();
        SQLiteDatabase gestor = bd();
        producto nuevoProducto = new producto();
        try{
            Cursor datos  = gestor.rawQuery("SELECT * FROM tb_producto",null);
            while(datos.moveToNext()){
                nuevoProducto.setId_pr(datos.getInt(0));
                nuevoProducto.setNom_pro(datos.getString(1));
                nuevoProducto.setDes_pro(datos.getString(2));
                nuevoProducto.setStock(datos.getInt(3));
                nuevoProducto.setPrecio(datos.getDouble(4));
                nuevoProducto.setUnidad(datos.getString(5));
                nuevoProducto.setEstado(datos.getInt(6));
                nuevoProducto.setCategoria(datos.getInt(7));
                nuevoProducto.setCategoriaString(this.obtenerLlave(datos.getString(8)));
                productosList.add(nuevoProducto);
                Log.i("Producto id: ", String.valueOf(nuevoProducto.getId_pr()));
                Log.i("Categoria ", nuevoProducto.getCategoriaString());


            }
        }catch (Exception ex){
            Log.i("Error Listar", ex.toString());
        }
        return productosList;
    }
    /*
        public ArrayList<producto> listarProducto2(){
            ArrayList<producto> productosList = new ArrayList<>();
            SQLiteDatabase gestor = bd();
            producto nuevoProducto = new producto();
            try{

                Cursor datos  = gestor.rawQuery("SELECT * FROM tb_producto",null);
                while(datos.moveToNext()){
                    nuevoProducto.setId_pr(datos.getInt(0));

                    productosList.add(nuevoProducto);
                }

            }catch (Exception ex){
                Log.i("Error Listar", ex.toString());
            }
            return productosList;
        }
    */
    public String obtenerLlave(String idcategoria){
        SQLiteDatabase gestor = bd();
        String llave = "Sin categoria";
        try{
            Cursor datos = gestor.rawQuery("SELECT nom_categoria FROM tb_categoria where id_categoria = ? ;", new String[]{String.valueOf(idcategoria)} );
            while (datos.moveToNext()){
                llave = datos.getString(0);
            }
        }catch (Exception ex){
            Log.i("obtener llave", ex.toString());
        }
        return llave;
    }

    //Funcion para insertar registros
    public boolean modificarProducto(producto us){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{

            int id =  us.getId_pr();
            String nom = us.getNom_pro();
            String des = us.getDes_pro();
            double stock = us.getStock();
            double precio = us.getPrecio();
            String unidad = us.getUnidad();
            int estad = us.getEstado();
            int cat = us.getCategoria();

            ContentValues registro = new ContentValues();
            registro.put("id_producto", id);
            registro.put("nombre_producto", nom);
            registro.put("des_producto", des);
            registro.put("stock", stock);
            registro.put("precio", precio);
            registro.put("unidad_de_medida", unidad);
            registro.put("estado_producto", estad);
            registro.put("categoria", cat);
            registro.put("fecha_entrada", getDateTime());

            int cant = (int) bd.update("tb_producto", registro, "id_producto=" + id, null);
            bd.close();
            if(cant>0) {
                estado = true;
            }else {
                estado = false;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }



    public boolean bajaIdProducto(final Context context, final producto po) {

        final boolean[] estadoDelete = {true};
        try {
            int id = po.getId_pr();
            Cursor fila = bd().rawQuery("select * from tb_producto where id_producto=" + id , null);
            if (fila.moveToFirst()) {
                po.setId_pr(Integer.parseInt(fila.getString(0)));
                po.setNom_pro(fila.getString(1));
                po.setDes_pro(fila.getString(2));
                po.setStock(Double.parseDouble(fila.getString(3)));
                po.setPrecio(Double.parseDouble(fila.getString(4)));
                po.setUnidad(fila.getString(5));
                po.setEstado(Integer.parseInt(fila.getString(6)));


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_borrar);
                builder.setTitle("Warning");
                builder.setMessage("¿Esta seguro de borrar el registro? \nid_producto: " + po.getId_pr() + "\nNombre: " + po.getNom_pro());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String[] parametros —— {String. valueOf(datos.getCodigo())},
                        int id = po.getId_pr();
                        int cant = bd().delete("tb_producto", "id_producto=" + id, null);
                        //bd(). delete(”articulos”,”codigo——?”,parametros),
                        if (cant > 0) {
                            estadoDelete[0] = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            estadoDelete[0] = false;
                        }
                        bd().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay resultados encontrados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            estadoDelete[0] = false;
            Log.e("Error.", e.toString());
        }
        return estadoDelete[0];
    }
}
