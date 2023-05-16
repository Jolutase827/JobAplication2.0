package com.example.myrecyclerviewexample.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer idUsuario;
    private String nombre;
    private String apellidos;
    private int idOficio;

    public Usuario(Integer imagen, String nombre, String apellidos, int oficio) {
        this.idUsuario = imagen;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idOficio = oficio;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getIdOficio() {
        return idOficio;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setIdOficio(int idOficio) {
        this.idOficio = idOficio;
    }

    @Override
    public int hashCode() {
        return idUsuario;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Usuario) {
            Usuario u = (Usuario) obj;
            return u.getIdUsuario()== idUsuario;
        }else
            return false;
    }
}
