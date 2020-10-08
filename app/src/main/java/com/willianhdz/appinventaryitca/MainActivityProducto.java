package com.willianhdz.appinventaryitca;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.willianhdz.appinventaryitca.data.Dtos.Categorias2;
import com.willianhdz.appinventaryitca.data.Dtos.producto;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;
import com.willianhdz.appinventaryitca.recycleview.RecyclerviewCategorias;
import com.willianhdz.appinventaryitca.recycleview.RecycleviewUsuario;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MainActivityProducto extends AppCompatActivity implements View.OnClickListener{
    private EditText Id_producto, nombreEdit, descripEdti, stockDescrip, precioEdit, unidadEdit;
    private Spinner estadoSpin, categoriaSpin;
    private Conexion_SQLite gestorBD;
    private producto nuevoProducto;
     int cateogoriaSeleccionada;
    ArrayList<Categorias2> categoriaslist;
    ArrayList<String> categ;
    Conexion_SQLite conexion = new Conexion_SQLite(this);
producto po = new producto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_producto);
        gestorBD = new Conexion_SQLite(this);
        //Binding conectar xml con java
        enlazar();
        prepararAdapter();

        categoriaSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cateogoriaSeleccionada = categoriaslist.get(i).getId_categoria();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cateogoriaSeleccionada = 0;
            }
        });
    }

    //////////////////Comentado bloque de menu no implementado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu,’ this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest. xml.
        int id = item.getItemId();

        //noinspection SimplifiablelfStatement
         if (id == R.id.action_listaCategorias) {
            Intent listViewActivity = new Intent(MainActivityProducto.this, RecyclerviewCategorias.class);
            startActivity(listViewActivity);
            return true;
        } else if (id == R.id.action_listaUsuarios) {
            Intent listViewActivity = new Intent(MainActivityProducto.this, RecycleviewUsuario.class);
            startActivity(listViewActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void guardarProducto(View v){
        if(comprobar(Id_producto)&& comprobar(nombreEdit) && comprobar(descripEdti) &&
                comprobar(stockDescrip) && comprobar(stockDescrip) &&
                comprobar(unidadEdit) && comprobar(precioEdit)){
            po.setId_pr(Integer.parseInt(Id_producto.getText().toString()));
            po.setNom_pro(nombreEdit.getText().toString());
            po.setDes_pro(descripEdti.getText().toString());
            po.setStock(Double.parseDouble(stockDescrip.getText().toString()));
            po.setPrecio(Double.parseDouble(precioEdit.getText().toString()));
            po.setUnidad(unidadEdit.getText().toString());
            int estado = (estadoSpin.getSelectedItem().toString() == "Activo") ? 1 : 0;
            po.setEstado(Integer.parseInt(String.valueOf(estado)));
            po.setCategoria(Integer.parseInt(String.valueOf(cateogoriaSeleccionada))); //cateogoriaSeleccionada
            if(conexion.InsertarProducto(po)){
                Toast.makeText(this, "Registro Guardado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void consultapornombre(View v) {
        try {
            if (comprobar(nombreEdit)) {

                if (gestorBD.consultarNombreProducto(nuevoProducto)) {
                    nombreEdit.setText(nuevoProducto.getNom_pro());
                    descripEdti.setText(nuevoProducto.getDes_pro());
                    stockDescrip.setText(valueOf(nuevoProducto.getStock()));
                    precioEdit.setText(valueOf(nuevoProducto.getPrecio()));
                    unidadEdit.setText(nuevoProducto.getUnidad());

                } else {
                    Toast.makeText(this, "No existe un artículo con dicho código",
                            Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error." + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void consultaporId(View v) {
        try {
            if (comprobar(Id_producto)) {
               String Id = Id_producto.getText().toString();
                po.setId_pr(Integer.parseInt(Id));
                if (conexion.consultarIdProducto(po)) {
                    nombreEdit.setText(po.getNom_pro());
                    descripEdti.setText(po.getDes_pro());
                    stockDescrip.setText(""+po.getStock());
                    precioEdit.setText(""+po.getPrecio());
                    unidadEdit.setText(po.getUnidad());

                } else {
                    Toast.makeText(this, "No existe un artículo con dicho código",
                            Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error." + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void bajaporcodigoPro(View v) {
        int Id = Integer.parseInt(Id_producto.getText().toString());
        po.setId_pr(Integer.parseInt(String.valueOf(Id)));
        if(conexion.bajaIdProducto(MainActivityProducto.this, po)){
            limpiarDatos();
        }else{
            Toast.makeText(this, "No existe un producto.",
                    Toast.LENGTH_SHORT).show();
            limpiarDatos();
        }
    }

    public void modificacion(View v) {
        if (comprobar(Id_producto)) {
            po.setId_pr(Integer.parseInt(Id_producto.getText().toString()));
            po.setNom_pro(nombreEdit.getText().toString());
            po.setDes_pro(descripEdti.getText().toString());
            po.setStock(Double.parseDouble(stockDescrip.getText().toString()));
            po.setPrecio(Double.parseDouble(precioEdit.getText().toString()));
            po.setUnidad(unidadEdit.getText().toString());
            int estado = (estadoSpin.getSelectedItem().toString() == "Activo") ? 1 : 0;
            po.setEstado(Integer.parseInt(String.valueOf(estado)));
            po.setCategoria(Integer.parseInt(String.valueOf(cateogoriaSeleccionada)));

            if (conexion.modificarProducto(po) == true) {
                Toast.makeText(this, "Registro Modificado Correctamente.",
                        Toast.LENGTH_SHORT).show();
                limpiarDatos();
            } else {
                Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }



/*
    public void listar(View v){
        Intent ventan = new Intent(this, MainActivityListarProductos.class);
        ventan.putExtra("opcion", "listar");
        startActivity(ventan);
    }

    public void Actualizar(View v){
        Intent ventan = new Intent(this, MainActivityListarProductos.class);
        ventan.putExtra("opcion", "actualizar");
        startActivity(ventan);
    }

    public void Eliminar(View v){
        Intent ventan = new Intent(this, MainActivityListarProductos.class);
        ventan.putExtra("opcion", "borrar");
        startActivity(ventan);
    }
*/
    public void enlazar(){
        Id_producto = findViewById(R.id.id_producto);
        nombreEdit = findViewById(R.id.nom_producto);
        descripEdti = findViewById(R.id.des_producto);
        stockDescrip = findViewById(R.id.stock_producto);
        precioEdit = findViewById(R.id.precio_producto);
        unidadEdit = findViewById(R.id.unidad_producto);
        estadoSpin = findViewById(R.id.estado_producto);
        categoriaSpin = findViewById(R.id.categoria_producto);
    }

    public void limpiarDatos() {
        nombreEdit.setText(null);
        descripEdti.setText(null);
        unidadEdit.setText(null);
        stockDescrip.setText(null);
        precioEdit.setText(null);

       Id_producto.requestFocus();
    }


    public void prepararAdapter(){
        // LLamar DAO producto
        SQLiteDatabase db=conexion.getReadableDatabase();
       //String[] categorias = new String[gestorBD.listarProducto().size()];
        Categorias2. categoria = null;
        categoriaslist = new ArrayList<Categorias2>();
        Cursor cursor = db.rawQuery("select * from tb_categoria", null);
        while (cursor.moveToNext()){
            Categorias2 categoria = new Categorias2();
            categoria.setId_categoria(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));
            categoria.setEstado(cursor.getInt(2));

            Log.i("id_categoria", String.valueOf(categoria.getId_categoria()));
            Log.i("nom_categoria",categoria.getNombre());
            Log.i("estado_categoria",String.valueOf(categoria.getEstado()));


            categoriaslist.add(categoria);
        }

        categ= new ArrayList<String>();


        for (int i = 0 ; i < categoriaslist.size(); i++){
            categ.add(categoriaslist.get(i).getId_categoria()+" ~ "+ categoriaslist.get(i).getNombre());
        }


        ArrayAdapter adapterCategorias = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categ);

        String[] estados = {"Activo", "Inactivo"};
        ArrayAdapter adapterEstado = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, estados);

        estadoSpin.setAdapter(adapterEstado);
        categoriaSpin.setAdapter(adapterCategorias);
    }

    public boolean comprobar(EditText campo){
        boolean estado;
        estado = false;
        if(campo.getText().toString().length() > 0){
            estado = true;
        }else {
            campo.setError("Campo Obligatorio");
        }
        return  estado;
    }

    @Override
    public void onClick(View view) {

    }
}