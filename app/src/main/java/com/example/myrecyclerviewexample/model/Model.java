package com.example.myrecyclerviewexample.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    public int insertUser(Usuario usuario) {
        MysqlDB mysqlDB = new MysqlDB();
        usuario.setImagen(usuarios.size());
        usuarios.add(usuario);
        return mysqlDB.addUser(usuario);
    }

    public int updateUser(Usuario u){
        MysqlDB mysqlDB = new MysqlDB();
        Usuario usuarioLista=  usuarios.stream()
                .filter(usuario -> usuario.getImagen() == u.getImagen())
                .findFirst()
                .get();
        usuarioLista.setNombre(u.getNombre());
        usuarioLista.setApellidos(u.getApellidos());
        usuarioLista.setOficio(u.getOficio());
        return mysqlDB.updateUser(u);

    }

    public int delecteUser(Usuario u){
        MysqlDB mysqlDB = new MysqlDB();
        usuarios.remove(u);
        return mysqlDB.deleteUser(u);
    }

}
