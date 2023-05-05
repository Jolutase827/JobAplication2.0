package com.example.myrecyclerviewexample.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int imagen;
    private String nombre;
    private String apellidos;
    private int oficio;

    public Usuario(int imagen, String nombre, String apellidos, int oficio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.oficio = oficio;
    }

    public int getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getOficio() {
        return oficio;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setOficio(int oficio) {
        this.oficio = oficio;
    }

    @Override
    public int hashCode() {
        return imagen;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Usuario) {
            Usuario u = (Usuario) obj;
            return u.getImagen()==imagen;
        }else
            return false;
    }
}
