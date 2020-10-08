package com.willianhdz.appinventaryitca.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.willianhdz.appinventaryitca.data.Dtos.categoria;
import com.willianhdz.appinventaryitca.data.db.Conexion_SQLite;

public class categoriaDAO {
    Conexion_SQLite bd;

    public categoriaDAO(Context app){
        bd = new Conexion_SQLite(app);
    }

    public void insertarCategoria(categoria datos){
        ContentValues categoriaValues = new ContentValues();
        try{
            categoriaValues.put("nom_categoria", datos.getNom_cat());
            categoriaValues.put("estado_categoria", datos.getEstado_cat());
            SQLiteDatabase gestor = bd.bd();
            if(gestor.insert("tb_categoria", null, categoriaValues)>0 ){
                Log.i("Correcto insert", "Todo anda de maravilla");
            };
        }catch (Exception ex){
            Log.i("insertCat", ex.toString());
        }
    }
}
