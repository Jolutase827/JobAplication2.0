package com.example.myrecyclerviewexample.model;

import com.example.myrecyclerviewexample.API.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MysqlDB {



    public List<Usuario> getAllUsers(){
         return Connector.getConector().getAsList(Usuario.class,"10.13.0.2:8080/api/usuarios");
    }

    public List<Oficio> getAllOficios() {
        return Connector.getConector().getAsList(Oficio.class,"10.13.0.2:8080/api/oficios");
    }

    public int addUser(Usuario usuario){
        usuario.setIdUsuario(null);
        return (Connector.getConector().post(Usuario.class,usuario,"10.13.0.2:8080/api/usuarios")!=null)?1:0;
    }

    public int addWithIdUser(Usuario usuario){
        return (Connector.getConector().post(Usuario.class,usuario,"10.13.0.2:8080/api/usuarios")!=null)?1:0;
    }

    public int updateUser(Usuario u)  {
        return (Connector.getConector().put(Usuario.class,u,"10.13.0.2:8080/api/usuarios")!=null)?1:0;
    }
    public int deleteUser(Usuario u){
        return (Connector.getConector().delete(Usuario.class,"10.13.0.2:8080/api/usuarios/"+u.getIdUsuario())!=null)?1:0;
    }
}
