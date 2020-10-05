package com.willianhdz.appinventaryitca.data.Dtos;


import java.io.Serializable;

//Para enviar objetos entre actividades (Como parâmetro) se coloca el ”implements Serializable”
public class Categorias2 implements Serializable {
    //Atributos de la clase
    int id_categoria;
    String nombre;
    int estado;

    public Categorias2() {
        //vacio
    }

    public Categorias2(int id_categoria, String nombre, int estado) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

