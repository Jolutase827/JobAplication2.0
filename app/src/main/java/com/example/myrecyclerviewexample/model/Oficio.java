package com.example.myrecyclerviewexample.model;

import java.io.Serializable;

public class Oficio implements Serializable {
    private int idOficio;
    private String descripcion;

    private byte[] image;

    private String url;
    public Oficio(int idOficio, String descripcion,byte[] image, String urlimagen) {
        this.idOficio=idOficio;
        this.descripcion=descripcion;
        this.image = image;
        this.url = urlimagen;
    }

    public int getIdOficio() {
        return idOficio;
    }

    public void setIdOficio(int idOficio) {
        this.idOficio = idOficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlimagen() {
        return url;
    }

    @Override
    public String toString() {
        return  descripcion;
    }
}
