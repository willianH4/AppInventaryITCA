package com.willianhdz.appinventaryitca.recycleview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.willianhdz.appinventaryitca.R;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;

public class RecyclerviewCategorias extends AppCompatActivity {
    private RecyclerView recycler;
    private AdaptadorCategorias adaptadorcategorias;
     Conexion_SQLite datos = new Conexion_SQLite(RecyclerviewCategorias.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_categorias);

        recycler = (RecyclerView)findViewById(R.id.rviewcategoria);
        //Esta linea mejora el rendimiento, si sabemos que el contenido
        //no va a afectar el tama;p del recycler
        recycler.setHasFixedSize(true);

        //Nuestro Recyclerview usara un Linear layout manager
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //adaptadorArticulos = new AdaptadorArticulos(consulta_recyclerView.this,
        //obtenerArticulos());

        adaptadorcategorias = new AdaptadorCategorias(RecyclerviewCategorias.this,
                datos.mostrarCategorias());
        recycler.setAdapter(adaptadorcategorias);
    }
}