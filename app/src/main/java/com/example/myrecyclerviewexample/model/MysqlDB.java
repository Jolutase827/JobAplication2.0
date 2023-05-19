package com.example.myrecyclerviewexample.model;

import com.example.myrecyclerviewexample.API.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MysqlDB {



    public List<Usuario> getAllUsers(){
         return Connector.getConector().getAsList(Usuario.class,"usuarios");
    }

    public List<Oficio> getAllOficios() {
        return Connector.getConector().getAsList(Oficio.class,"oficios");
    }

    public Usuario addUser(Usuario usuario){
        usuario.setIdUsuario(null);
        return Connector.getConector().post(Usuario.class,usuario,"usuarios");
    }

    public Imagen getImage(int id){
        return (Connector.getConector().get(Imagen.class,"oficios/"+id+"/image"));
    }
    public int addWithIdUser(Usuario usuario){
        return (Connector.getConector().post(Usuario.class,usuario,"usuarios")!=null)?1:0;
    }

    public Usuario updateUser(Usuario u)  {
        return (Connector.getConector().put(Usuario.class,u,"usuarios"));
    }
    public int deleteUser(Usuario u){
        return (Connector.getConector().delete(Usuario.class,"usuarios/"+u.getIdUsuario())!=null)?1:0;
    }
}
