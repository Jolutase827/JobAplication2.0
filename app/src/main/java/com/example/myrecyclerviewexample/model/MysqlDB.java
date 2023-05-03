package com.example.myrecyclerviewexample.model;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlDB {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection("jdbc:mysql://10.13.0.2:3306/jobAplication","jose","1234");
    }

    public List<Usuario> getAllUsers(){
        List<Usuario> usuarios = new ArrayList<>();

        try(Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario")
        ){
            int id,oficio;
            String nombre,apellidos;
            while(rs.next()){
                id=rs.getInt("idUsuario");
                nombre = rs.getString("nombre");
                apellidos= rs.getString("apellidos");
                oficio = rs.getInt("idOficio");
                usuarios.add(new Usuario(id,nombre,apellidos,oficio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<Oficio> getAllOficios() {
        List<Oficio> oficios = new ArrayList<>();

        try(Connection c = getConnection();
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Oficio")
        ){

            int idOficio;
            String descripcion;

            while (rs.next()){
                idOficio = rs.getInt("idOficio");
                descripcion = rs.getString("descripcion");
                oficios.add(new Oficio(idOficio,descripcion));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oficios;
    }

    public int addUser(Usuario usuario){

        try(Connection c = getConnection();
            Statement stmt = c.createStatement();){
            return stmt.executeUpdate("INSERT INTO Usuario(nombre,apellidos,idOficio) VALUES('"+usuario.getNombre()+"','"+usuario.getApellidos()+"',"+usuario.getOficio()+")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
