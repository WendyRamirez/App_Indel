package com.example.programarotacionbeta2.Adapters;

public class DatosUsuarios {
    private String username;
    private int id_usuario;

    public DatosUsuarios(String username, int id_usuario) {
        this.username = username;
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "DatosUsuarios{" +
                "username='" + username + '\'' +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
