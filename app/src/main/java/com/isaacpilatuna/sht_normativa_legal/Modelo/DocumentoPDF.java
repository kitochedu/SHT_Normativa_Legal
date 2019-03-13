package com.isaacpilatuna.sht_normativa_legal.Modelo;

import java.util.Date;

public class DocumentoPDF {
    String titulo;
    String autor;
    Date fecha;
    String url;

    public DocumentoPDF(String titulo, String autor, Date fecha, String url){
        this.titulo=titulo;
        this.autor=autor;
        this.fecha=fecha;
        this.url=url;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
