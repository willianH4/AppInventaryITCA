package com.willianhdz.appinventaryitca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.willianhdz.appinventaryitca.ui.Usuario.MainActivityUsuario;

public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3,btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btn1=findViewById(R.id.btn_1);
       btn2=findViewById(R.id.btn_2);
       btn3=findViewById(R.id.btn_3);
        btn4=findViewById(R.id.btn_4);

       btn1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent listViewActivity = new Intent(MainActivity.this, MainActivityUsuario.class);
               startActivity(listViewActivity);
           }
       });

       btn2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent listViewActivity = new Intent(MainActivity.this, MainActivityCategoria.class);
               startActivity(listViewActivity);
           }
       });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listViewActivity = new Intent(MainActivity.this, MainActivityProducto.class);
                startActivity(listViewActivity);
            }
        });

       btn4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent listViewActivity = new Intent(MainActivity.this, Acerca_dexd.class);
               startActivity(listViewActivity);
           }
       });
    }

}