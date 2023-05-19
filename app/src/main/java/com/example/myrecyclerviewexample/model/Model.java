package com.example.myrecyclerviewexample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Model {

    private static Model model;
    private List<Usuario> usuarios;
    private List<Oficio> oficios;

    private Model() {
        usuarios = new ArrayList<>();
        oficios = new ArrayList<>();
    }


    public static Model getInstance() {
        if (model == null)
            model = new Model();

        return model;
    }

    public List<Usuario> getUsuarios() {
        if (usuarios.isEmpty()) {
            MysqlDB mysqlDB = new MysqlDB();
            usuarios = mysqlDB.getAllUsers();
        }
        return usuarios;
    }


    public List<Oficio> getOficios() {
        if (oficios.isEmpty()) {
            MysqlDB mysqlDB = new MysqlDB();
            oficios = mysqlDB.getAllOficios();
        }
        return oficios;
    }

    public Imagen getImagen(int id){
        MysqlDB mysqlDB = new MysqlDB();
        return mysqlDB.getImage(id);
    }

    public int insertUser(Usuario usuario) {
        MysqlDB mysqlDB = new MysqlDB();
        Usuario u = mysqlDB.addUser(usuario);
        usuarios.add(u);
        return u!=null?1:0;
    }

    public int undoDelete(Usuario u){
        MysqlDB mysqlDB = new MysqlDB();
        usuarios.add(u);
        usuarios.sort( (u1,u2) -> u1.getIdUsuario()-u2.getIdUsuario());
        int tablas = mysqlDB.addWithIdUser(u);
        return tablas;
    }

    public int updateUser(Usuario u){
        MysqlDB mysqlDB = new MysqlDB();
        Usuario  usuario = usuarios.stream().filter(usuario1 -> Objects.equals(usuario1.getIdUsuario(), u.getIdUsuario())).findFirst().get();
        usuario.setNombre(u.getNombre());
        usuario.setApellidos(u.getApellidos());
        usuario.setIdOficio(u.getIdOficio());
        return mysqlDB.updateUser(u)!=null?1:0;

    }

    public int delecteUser(Usuario u){
        MysqlDB mysqlDB = new MysqlDB();
        usuarios.remove(u);
        return mysqlDB.deleteUser(u);
    }

}
