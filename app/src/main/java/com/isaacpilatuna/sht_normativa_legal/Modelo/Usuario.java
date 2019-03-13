package com.isaacpilatuna.sht_normativa_legal.Modelo;

public class Usuario {
    String nombreCompleto;
    String correo;
    String paquete;

    public Usuario(String nombreCompleto, String correo, String paquete) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.paquete = paquete;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }
}
