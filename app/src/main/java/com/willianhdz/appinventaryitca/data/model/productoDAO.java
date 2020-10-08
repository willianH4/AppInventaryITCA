package com.willianhdz.appinventaryitca.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.willianhdz.appinventaryitca.data.Dtos.producto;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;

import java.util.ArrayList;

public class productoDAO {
    Conexion_SQLite db;
    producto tablaproducto;

    public productoDAO(Context app) {
        // Inicializar bd
        db = new Conexion_SQLite(app);
    }

    public boolean insertaarProducto(producto datos){
        ContentValues productoValues = new ContentValues();
        try{
            productoValues.put("nommbre_producto", datos.getNom_pro());
            productoValues.put("des_producto", datos.getDes_pro());
            productoValues.put("stock", datos.getStock());
            productoValues.put("precio", datos.getPrecio());
            productoValues.put("unidad_de_medida", datos.getUnidad());
            productoValues.put("estado_producto", datos.getEstado());
            productoValues.put("categoria",datos.getCategoria());

            SQLiteDatabase gestor = db.bd();;
            if(gestor.insert("tb_producto", null,productoValues) < 0){
                Log.i("insertar ", "Error de insercion");
            }
        }catch (Exception ex){
            Log.i("Error insertar ", ex.toString());
        }
        return true;
    }

    public ArrayList<producto> listarProducto(){
        ArrayList<producto> productosList = new ArrayList<>();
        SQLiteDatabase gestor = db.bd();
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
}
