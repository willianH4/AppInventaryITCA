package com.willianhdz.appinventaryitca.data.Dtos;

import java.io.Serializable;

public class categoria implements Serializable {
int id_cat;
String nom_cat;
int estado_cat;

public categoria() {

}

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    public int getEstado_cat() {
        return estado_cat;
    }

    public void setEstado_cat(int estado_cat) {
        this.estado_cat = estado_cat;
    }
}
