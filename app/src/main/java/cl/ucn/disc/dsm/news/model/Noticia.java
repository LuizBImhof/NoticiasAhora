/*
 * Copyright (c) 2019 Diego Urrutia-Astorga.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cl.ucn.disc.dsm.news.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Noticia en la aplicacion.
 *
 * @author Diego Urrutia-Astorga.
 */

@Entity(tableName = "noticia")
public class Noticia {

    /**
     * ZoneId
     */
    public static final ZoneId ZONE_ID = ZoneId.of("-3");


    /**
     * Id (unique)
     */
    @PrimaryKey(autoGenerate = false)
    private final Long id;

    /**
     * Titulo
     */
    @NonNull
    private final String titulo;

    /**
     * The fuente
     */
    private final String fuente;

    /**
     * The Url
     */
    private final String url;

    /**
     * lugar donde se encuentra la foto
     */
    @ColumnInfo(name = "url_foto")
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
    @NonNull
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
     */
    public Noticia(Long id, String titulo, String fuente, String url, String urlFoto, String resumen, String contenido, ZonedDateTime fecha, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.fuente = fuente;
        this.url = url;
        this.urlFoto = urlFoto;
        this.resumen = resumen;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }

    public Long getId() {
        return id;
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
