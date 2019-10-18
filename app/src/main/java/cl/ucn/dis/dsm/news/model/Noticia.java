/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.dis.dsm.news.model;


import org.threeten.bp.ZonedDateTime;

public class Noticia {

    /**
     * Titulo
     */
    private final String titulo;

    /**
     * fuente
     */
    private final String fuente;

    /**
     * The Url
     */
    private final String url;

    /**
     * lugar donde se encuentra la foto
     */
    private final String urlFoto;

    /**
     * Resumen de la noticia
     */
    private final String resumen;

    /**
     * contenido, el texto principal de la noticia
     */
    private final String contenido;

    /**
     * fecha, con hora de la publicacion de la noticia
     */
    private final ZonedDateTime fecha;

    /**
     * Autor, el que escribio la noticia
     */
    private final String autor;

    public String getResumen() {
        return resumen;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Constructor
     * @param titulo
     * @param fuente
     * @param url
     * @param urlFoto
     * @param resumen
     * @param contenido
     * @param fecha
     * @param autor
     */
    public Noticia(String titulo, String fuente, String url, String urlFoto, String resumen, String contenido, ZonedDateTime fecha, String autor) {
        this.titulo = titulo;
        this.fuente = fuente;
        this.url = url;
        this.urlFoto = urlFoto;
        this.resumen = resumen;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }


    public String getFuente() {
        return fuente;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getContenido() {
        return contenido;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }
}
