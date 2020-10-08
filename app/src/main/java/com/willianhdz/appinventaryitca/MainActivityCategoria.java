package com.willianhdz.appinventaryitca;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.willianhdz.appinventaryitca.data.Dtos.Categorias2;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.willianhdz.appinventaryitca.recycleview.RecyclerviewCategorias;
import com.willianhdz.appinventaryitca.recycleview.RecycleviewUsuario;
import com.willianhdz.appinventaryitca.ui.usuario.MainActivityUsuario;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivityCategoria extends AppCompatActivity implements View.OnClickListener{
    private FABToolbarLayout morph; //agregado para la toolbar

    private EditText et_id, et_nombre, et_estado;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar, btn_listar;
    private TextView tv_resultado;

    boolean inputEt=false;
    boolean inputEd=false;
    boolean input1=false;
    int resultadoInsert=0;

    //Modal ventanas = new Modal();
    Conexion_SQLite conexion = new Conexion_SQLite(this);
    Categorias2 datos = new Categorias2();
    AlertDialog.Builder dialogo;

    /*
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                new android.app.AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_close)
                        .setTitle("Warning")
                        .setMessage("¿Realmente desea salir?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {//un listener que at pulsar, cierre la aplicacion

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /*lntent intent —— new Intent(DashboardLuces.this, luces control sms.class), startActivity(intent),’”/
            //MainActivity.this.finishAffinity(),
            //finish()

                                finish();
                                Intent Retornar = new Intent(MainActivityCategoria.this, MainActivity.class);
                                startActivity(Retornar);
                            }
                        })
                        .show();

                // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
                return true;
            }

            //para las demas cosas, se reenvia el evento at listener habitual
            return super.onKeyDown(keyCode, event);
        }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_maincategoria);
/*
        //Inicia codigo para la Toolbar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        View uno, dos, tres, cuatro, cinco, seis;

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);
        cuatro = findViewById(R.id.cuatro);
        cinco = findViewById(R.id.cinco);
        seis = findViewById(R.id.seis);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
//Finaliza codigo para la Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.hola));
        toolbar.setTitleTextColor(getResources().getColor(R.color.mycolor1));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("Proyecto Inventario");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.mycolor));
        toolbar.setTitle("By HGroup");
        setSupportActionBar(toolbar);

        //JAJA EL MISMO ERROR DEL NINIO, DAME UN MOMENTO MIENTRAS ABRO MI PROYECTO dale crack, EL ERROR ESTA EN EL MANIFEST
        // Y EN STYLES, O YA VISTE ESOS DOS_? solo styles he revisado el manifest neles
        //VAYA MIRA REY, TA SENCILLO, YO ME LLEV VARIAS HORAS PENDEJEANDO XD EL PROFE LA CAGA Y NO AVISA DE ESE ERROR

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        /* comentado para probar la Toolbar
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Snackbar.make(view, ”Replace with your own action”, Snackbar. LENGTH LONG)
                //	. setAction(”Action”, null). show(),
                ventanas.Search(MainActivity.this);

            }
        });

*/


        et_id = (EditText) findViewById(R.id.et_id);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_estado = (EditText) findViewById(R.id.et_estado);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_consultar1 = (Button) findViewById(R.id.btn_consultar1);
        btn_consultar2 = (Button) findViewById(R.id.btn_consultar2);
        btn_eliminar = (Button) findViewById(R.id.btn_eliminar);
        btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        btn_listar = (Button)findViewById(R.id.btn_listacate);
        //tv resultado —— (TextView) findViewById(R.id.tv resultado);

        btn_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Recyclecate = new Intent(MainActivityCategoria.this, RecyclerviewCategorias.class);
                startActivity(Recyclecate);
            }
        });

        String senal = "";
        String id = "";
        String nombre = "";
        String estado = "";

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                id = bundle.getString("id");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                estado = bundle.getString("estado");
                if (senal.equals("1")) {
                    et_id.setText(id);
                    et_nombre.setText(nombre);
                    et_estado.setText(estado);
                    //finish(),
                }
            }
        }catch (Exception e) {

        }
    }

    private void confirmacion() {
        String mensaje = "¿Realmente desea salir?";
        dialogo = new AlertDialog.Builder(MainActivityCategoria.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
        /*lntent intent —— new Intent(DashboardLuces.this, luces control sms. class), startActivity(intent),”/
        //DashboardLuces.this. finishAffinity(), */
                //   MainActivityCategoria.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                //Toast. makeText(getApplicationContext(), "Operacion Cancelada.", Toast. LENGTH LONG). show(),’
            }
        });
        dialogo.show();
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

        if (id == R.id.action_listaCategorias) {
            Intent listViewActivity = new Intent(MainActivityCategoria.this, RecyclerviewCategorias.class);
            startActivity(listViewActivity);
            return true;
        } else if (id == R.id.action_listaUsuarios) {
            Intent listViewActivity = new Intent(MainActivityCategoria.this, RecycleviewUsuario.class);
            startActivity(listViewActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

///////////////////////////Finaliza bloque del menu no usado

    public void alta(View v) {
        if(et_id.getText().toString().length()==0){
            et_id.setError("Campo obligatorio");
            inputEt = false;
        }else {
            inputEt = true;
        }
        if(et_nombre.getText().toString().length()==0){
            et_nombre.setError("Campo obligatorio");
            inputEd = false;
        }else {
            inputEd = true;
        }
        if(et_estado.getText().toString().length()==0){
            et_estado.setError("Campo obligatorio");
            input1 = false;
        }else {
            input1 = true;
        }
        if (inputEt && inputEd && input1){
            try {
                datos.setId_categoria(Integer.parseInt(et_id.getText().toString()));
                datos.setNombre(et_nombre.getText().toString());
                datos.setEstado(Integer.parseInt(et_estado.getText().toString()));
                //if(conexion.insertardatos(datos))(
                //if(conexion.InsertRegister(datos)){

                if(conexion.InserCategorias(datos)){

                    Toast.makeText(this, "Registro agregado satisfactoriamente!",
                            Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }else {
                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro\n" +
                            " ID: " + et_id.getText().toString(), LENGTH_LONG).show();
                    limpiarDatos();
                }

            }catch (Exception e) {
                Toast.makeText(this, "ERROR. Ya existe.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void mensaje (String mensaje) {
        Toast.makeText(this, "" + mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos() {
        et_id.setText(null);
        et_nombre.setText(null);
        et_estado.setText(null);
        et_id.requestFocus();
    }

    public void consultaporcodigo(View v) {
        if(et_id.getText().toString().length()==0){
            et_id.setError("Campo obligatorio");
            inputEt = false;
        }else {
            inputEt = true;
        }
        if(inputEt){
            String codigo = et_id.getText().toString();
            datos.setId_categoria(Integer.parseInt(codigo));
            //if(conexion.consultaCodigo(datos)){
            if(conexion.consultaIdCategorias(datos)){
                et_nombre.setText(datos.getNombre());
                et_estado.setText(""+datos.getEstado());
                //Toast. makeText(this, "Se encontro uno", Toast. LENGTH SHORT). show(),
            }else {
                Toast.makeText(this, "No existe un articulo con dicho ID",
                        Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else {
            Toast.makeText(this, "Ingrese el ID del articulo a buscar.",
                    Toast.LENGTH_SHORT).show();
        }}

    public void consultapordescripcion(View v){
        if(et_nombre.getText().toString().length()==0){
            et_nombre.setError("Campo obligatorio");
            inputEd = false;
        }else {
            inputEd = true;
        }if(inputEd){
            String nombre = et_nombre.getText().toString();
            datos.setNombre(nombre);
            if(conexion.consultarNombreCategoria(datos)){
                et_id.setText(""+datos.getId_categoria());
                et_nombre.setText(datos.getNombre());
                et_estado.setText(""+datos.getEstado());
                //Toast. makeText(this, "Se encontro uno", Toast. LENGTH SHORT). show(),
            }else {
                Toast.makeText(this, "No existe una categoria con dicho nombre",
                        Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }

        }else {
            Toast.makeText(this, "Ingrese el nombre de la categoria a buscar.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void bajaporcodigo(View v) {
        if(et_id.getText().toString().length()==0){
            et_id.setError("campo obligatorio");
            inputEt = false;
        }else {
            inputEt = true;
        }
        if(inputEt){
            String idCat = et_id.getText().toString();
            datos.setId_categoria(Integer.parseInt(idCat));
            if(conexion.bajaIdCategorias(MainActivityCategoria.this,datos)){
                //Toast. makeText(this, "Registro eliminado satisfactoriamente.", Toast. LENG TH SHORT). show(),
                limpiarDatos();
            }else {
                Toast.makeText(this, "No existe una categoria con dicho ID.",
                        Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }


    public void modificacion(View v) {
        if (et_id.getText().toString().length() == 0) {
            et_id.setError("campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;

        }
        if (inputEt) {

            String id = et_id.getText().toString();
            String nombre = et_nombre.getText().toString();
            String estado = et_estado.getText().toString();

            datos.setId_categoria(Integer.parseInt(id));
            datos.setNombre(nombre);
            datos.setEstado(Integer.parseInt(estado));
            if (conexion.modificarCategorias(datos)) {
                Toast.makeText(this, "Registro Modificado Correctamente.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onClick(View view) {

    }
/*
    //metodo de la toolbarr
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            morph.show();
        }
        morph.hide();
        if (v.getId() == R.id.uno){
            et_id.setText(null);
            et_nombre.setText(null);
            et_estado.setText(null);
            Toast.makeText(getApplicationContext(),"Campos limpiados correctamente",Toast.LENGTH_SHORT).show();

        }else if (v.getId() == R.id.dos){
            Intent listViewActivity = new Intent(MainActivityCategoria.this, RecyclerviewCategorias.class);
            startActivity(listViewActivity);

        }else if (v.getId() == R.id.tres){
            Intent listViewActivity = new Intent(MainActivityCategoria.this, RecyclerviewCategorias.class);
            startActivity(listViewActivity);

        }else if (v.getId() == R.id.cuatro){
            Intent listViewActivity = new Intent(MainActivityCategoria.this, RecyclerviewCategorias.class);
            startActivity(listViewActivity);
        }
    }
    */

}