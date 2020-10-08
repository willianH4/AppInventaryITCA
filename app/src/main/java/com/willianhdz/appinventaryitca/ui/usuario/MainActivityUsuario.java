package com.willianhdz.appinventaryitca.ui.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.willianhdz.appinventaryitca.MainActivityCategoria;
import com.willianhdz.appinventaryitca.R;
import com.willianhdz.appinventaryitca.data.Dtos.usuario;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;
import com.willianhdz.appinventaryitca.recycleview.RecyclerviewCategorias;
import com.willianhdz.appinventaryitca.recycleview.RecycleviewUsuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivityUsuario extends AppCompatActivity {

    //Declaracion de variables
    private EditText txtnom, txtape, txtemail, txtuser,txtpass, txtrespuesta, txtid;
    private Spinner spntipo, spnpregunta, spnestado;
    private Button btng, btnconcode, btned, btnel, btnlista;

    //Instancias
    Conexion_SQLite conexion = new Conexion_SQLite(this);
    usuario datos = new usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        // Asignacion a las variables
        txtnom = findViewById(R.id.usuario_nombre);
        txtape = findViewById(R.id.usuario_apellido);
        txtemail = findViewById(R.id.usuario_correo);
        txtuser = findViewById(R.id.usuario_user);
        txtpass = findViewById(R.id.usuario_contrasena);
        txtrespuesta = findViewById(R.id.usuario_respuesta);
        txtid = findViewById(R.id.usuario_id);

        spntipo = findViewById(R.id.usuaio_tipo);
        spnestado = findViewById(R.id.usuaio_estado);
        spnpregunta = findViewById(R.id.usuaio_pregunta);

        btng = findViewById(R.id.btn_guardar);
        btnconcode = findViewById(R.id.btn_consultacodigo);
        btned = findViewById(R.id.btn_editar);
        btnel = findViewById(R.id.btn_borrar);

        btnlista = findViewById(R.id.btn_listausu);

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Recyclecate = new Intent(MainActivityUsuario.this, RecycleviewUsuario.class);
                startActivity(Recyclecate);
            }
        });

        //Informacion Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TipoUsuario, android.R.layout.simple_spinner_item);
        spntipo.setAdapter(adapter);
        spntipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivityUsuario.this, "Seleccionaste: " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                String tipo = String.valueOf(parent.getItemIdAtPosition(position));
                int val;
                switch (tipo){
                    case "2":
                        val = 3;
                        datos.setTipo(val);
                        break;
                    case "1":
                        val = 2;
                        datos.setTipo(val);
                        break;
                    case "0":
                        val = 1;
                        datos.setTipo(val);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        spnestado.setAdapter(adapter1);
        spnestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivityUsuario.this, "Seleccionaste: " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                String est = String.valueOf(parent.getItemIdAtPosition(position));
                int val;
                switch (est){
                    case "0":
                        val = 1;
                        datos.setEstado_us(val);
                        break;
                    case "1":
                        val = 2;
                        datos.setEstado_us(val);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Preguntas, android.R.layout.simple_spinner_item);
        spnpregunta.setAdapter(adapter2);
        spnpregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivityUsuario.this, "Seleccionaste: " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                String est = String.valueOf(parent.getItemIdAtPosition(position));
                switch (est){
                    case "0":
                        datos.setPregunta("¿Nombre de tu mama?");
                        break;
                    case "1":
                        datos.setPregunta("¿Color favorito?");
                        break;
                    case "2":
                        datos.setPregunta("¿Deporte favorito?");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

        //Limpia los TextView
        public void limpiar(){
            txtnom.setText(""); txtnom.setText("");
            txtape.setText(""); txtemail.setText("");
            txtuser.setText(""); txtpass.setText("");
            txtrespuesta.setText(""); txtid.setText("");
        }

        //Guardar un registro
        public void alta(View v){
            try {
                datos.setId_usu(Integer.parseInt(txtid.getText().toString()));
                datos.setNombre(txtnom.getText().toString());
                datos.setApellido(txtape.getText().toString());
                datos.setCorreo(txtemail.getText().toString());
                datos.setUsuario(txtuser.getText().toString());
                datos.setClave(txtpass.getText().toString());
                datos.setRespuesta(txtrespuesta.getText().toString());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
                Date date = new Date();
                if (conexion.InsertarUsuario(datos) == true){
                    Toast.makeText(this, "Registro agregado correctamente", Toast.LENGTH_SHORT).show();
                    limpiar();
                }else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    limpiar();
                }
            }catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    public void consultaporcodigo(View v) {
        try {
            String usuario = txtid.getText().toString();

            if (conexion.consultaidUsuario(datos) == true) {
                txtnom.setText(datos.getNombre());
                txtape.setText(datos.getApellido());
                txtemail.setText(datos.getCorreo());
                txtrespuesta.setText(datos.getRespuesta());
                txtpass.setText(datos.getClave());
                txtuser.setText(datos.getUsuario());
            } else {
                Toast.makeText(this, "No existe un artículo con dicho código",
                        Toast.LENGTH_SHORT).show();
                limpiar();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error." + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void bajaporcodigo(View v) {
        String user = txtid.getText().toString();
        datos.setUsuario(user);
         if(conexion.bajaidUsuario(MainActivityUsuario.this,datos) == true){
                limpiar();
         }else{
                Toast.makeText(this, "No existe un artículo con dicho usuario.",
                        Toast.LENGTH_SHORT).show();
                limpiar();
         }
    }

    public void modificacion(View v) {
        datos.setId_usu(datos.getId_usu());
        datos.setNombre(txtnom.getText().toString());
        datos.setApellido(txtape.getText().toString());
        datos.setCorreo(txtemail.getText().toString());
        datos.setUsuario(txtuser.getText().toString());
        datos.setClave(txtpass.getText().toString());
        datos.setRespuesta(txtrespuesta.getText().toString());

        if(conexion.modificarUsuario(datos) == true){
            Toast.makeText(this, "Registro Modificado Correctamente.",
                        Toast.LENGTH_SHORT).show();
            limpiar();
        }else{
            Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
}