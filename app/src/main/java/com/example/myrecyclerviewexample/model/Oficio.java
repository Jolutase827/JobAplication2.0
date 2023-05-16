package com.example.myrecyclerviewexample.model;

import java.io.Serializable;

public class Oficio implements Serializable {
    private int idOficio;
    private String descripcion;

    private byte[] image;

    private String urlimagen;
    public Oficio(int idOficio, String descripcion) {
        this.idOficio=idOficio;
        this.descripcion=descripcion;
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

    @Override
    public String toString() {
        return  descripcion;
    }
}
