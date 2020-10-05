package com.willianhdz.appinventaryitca.data.Dtos;

import java.io.Serializable;

public class producto implements Serializable {
int  id_pr;
String nom_pro;
String des_pro;
double stock;
double precio;
String unidad;
int estado;
int categoria;


public producto() {
}

    public producto(int id_pr, String nom_pro, String des_pro, double stock, double precio, String unidad, int estado, int categoria) {
        this.id_pr = id_pr;
        this.nom_pro = nom_pro;
        this.des_pro = des_pro;
        this.stock = stock;
        this.precio = precio;
        this.unidad = unidad;
        this.estado = estado;
        this.categoria = categoria;
    }


    public int getId_pr() {
        return id_pr;
    }

    public void setId_pr(int id_pr) {
        this.id_pr = id_pr;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public String getDes_pro() {
        return des_pro;
    }

    public void setDes_pro(String des_pro) {
        this.des_pro = des_pro;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

}

