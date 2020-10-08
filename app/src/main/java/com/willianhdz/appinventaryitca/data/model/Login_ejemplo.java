package com.willianhdz.appinventaryitca.data.model;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.willianhdz.appinventaryitca.MainActivity;
import com.willianhdz.appinventaryitca.R;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;
import com.willianhdz.appinventaryitca.ui.usuario.MainActivityUsuario2;

public class Login_ejemplo extends AppCompatActivity {

    Button btn_ingresar, nuevo;

    Conexion_SQLite cn = new Conexion_SQLite(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ejemplo);




        btn_ingresar= (Button) findViewById(R.id.btn_ingresar);
        nuevo= (Button) findViewById(R.id.nuevo);


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText contra = (EditText) findViewById(R.id.contra);
                EditText correo = (EditText) findViewById(R.id.correo);

                if (correo.length() == 0) {
                    correo.setError("Este campo es obligatorio");
                } else if (contra.length() == 0) {
                    contra.setError("Este campo es obligatorio");

                } else {

                    try {

                        Cursor cursor = cn.consultarusu(correo.getText().toString(), contra.getText().toString());

                        if (cursor.getCount() > 0) {

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {

                            Toast.makeText(Login_ejemplo.this, "Usuario Invalido", Toast.LENGTH_LONG).show();
                        }

                        correo.setText("");
                        contra.setText("");
                        correo.findFocus();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityUsuario2.class);
                startActivity(intent);
            }
        });

    }
}