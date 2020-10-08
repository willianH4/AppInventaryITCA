package com.willianhdz.appinventaryitca.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.willianhdz.appinventaryitca.R;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;
import com.willianhdz.appinventaryitca.data.Dtos.usuario;
import java.util.ArrayList;
import java.util.List;



public class RecycleviewUsuario extends AppCompatActivity {
    private RecyclerView recycler;
    private AdaptadorUsuario adaptadorUsuario;
    Conexion_SQLite datos = new Conexion_SQLite(RecycleviewUsuario.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_usuario);
        recycler = (RecyclerView)findViewById(R.id.rviewUsuario);
// Esta línea mejora el rendimiento, si sabemos que el contenido
// no va a afectar al tamaño del RecyclerView
        recycler.setHasFixedSize(true);
// Nuestro RecyclerView usará un linear layout manager
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adaptadorUsuario = new AdaptadorUsuario(RecycleviewUsuario.this,
                datos.mostrarUsuario());
        recycler.setAdapter(adaptadorUsuario);
    }


    /*public List<usuario> obtenerUsuario() {
        List<usuario> usuarios = new ArrayList<>();
        usuarios.add(new usuario(5, "juan","lopez","juan@gmail.com","juan123","123",1,1,"¿ Cual es tu color favorito?", "el negro"));
       // articulos.add(new Dto(2, "Impresora HP", 100.78));
        //articulos.add(new Dto(3, "Disco Duro 1TB", 100.19));
        return usuarios;
    }*/


}