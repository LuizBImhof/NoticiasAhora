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

import org.threeten.bp.ZonedDateTime;

public class NoticiaBuilder {
    private long id;
    private String titulo;
    private String fuente;
    private String autor;
    private String url;
    private String urlFoto;
    private String resumen;
    private String contenido;
    private ZonedDateTime fecha;

    public NoticiaBuilder setId(final Long id) {
        this.id = id;
        return this;
    }

    public NoticiaBuilder setTitulo(final String titulo) {
        this.titulo = titulo;
        return this;
    }

    public NoticiaBuilder setFuente(final String fuente) {
        this.fuente = fuente;
        return this;
    }

    public NoticiaBuilder setAutor(final String autor) {
        this.autor = autor;
        return this;
    }

    public NoticiaBuilder setUrl(final String url) {
        this.url = url;
        return this;
    }

    public NoticiaBuilder setUrlFoto(final String urlFoto) {
        this.urlFoto = urlFoto;
        return this;
    }

    public NoticiaBuilder setResumen(final String resumen) {
        this.resumen = resumen;
        return this;
    }

    public NoticiaBuilder setContenido(final String contenido) {
        this.contenido = contenido;
        return this;
    }

    public NoticiaBuilder setFecha(final ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public Noticia createNoticia() {
        return new Noticia(id, titulo, fuente, url, urlFoto, resumen, contenido, fecha, autor);
    }
}
