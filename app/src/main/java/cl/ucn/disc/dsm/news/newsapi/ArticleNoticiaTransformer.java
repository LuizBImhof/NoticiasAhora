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

package cl.ucn.disc.dsm.news.newsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

import cl.ucn.disc.dsm.news.Transformer;
import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.model.NoticiaBuilder;

/**
 * Transformacion de un {@link Article} para un {@link Noticia}
 *
 * @author Diego Urrutia-Astorga.
 * @author Luiz Artur Boing Imhof
 */

public final class ArticleNoticiaTransformer implements Transformer.NoticiaTransformer<Article> {

    /**
     * The logger
     */
    public static final Logger log = LoggerFactory.getLogger(ArticleNoticiaTransformer.class);

    /**
     * Convierte una fecha de {@link String} a una {@link ZonedDateTime}.
     *
     * @param fecha to parse.
     * @return the fecha.
     * @throws cl.ucn.disc.dsm.news.Transformer.NoticiaTransformerException en caso de no lograr convertir la fecha.
     */
    private static ZonedDateTime parse(final String fecha) {

        //La fecha no puede ser null para convertir
        if (fecha == null) {
            throw new Transformer.NoticiaTransformerException("Fecha del Article no puede ser null");
        }

        try {
            // Tratar de convertir la fecha ..
            return ZonedDateTime.parse(fecha);
        } catch (DateTimeParseException ex) {

            // Mensaje de debug
            log.error("No se puede parsear fecha: ->{}<-", fecha, ex);

            // Anido la DateTimeParseException en una NoticiaTransformerException.
            throw new Transformer.NoticiaTransformerException("Error al parsear la fecha", ex);
        }
    }

    /**
     * @see Transformer
     */
    @Override
    public Noticia transform(final Article article) {

        // Nullity
        if (article == null) {
            throw new Transformer.NoticiaTransformerException("Article fue null");
        }

        // Si el articulo es null ..
        if (article.title == null) {

            log.warn("Article sin title: {}", Transformer.toString(article));

            // .. y el contenido es null, lanzar exception!
            if (article.description == null) {
                throw new Transformer.NoticiaTransformerException("Article sin titulo y descripcion");
            }

            article.title = "No Title*";
        }

        // En caso de no haber una fuente.
        if (article.source == null) {
            article.source = new Source();
            article.source.name = "No Source*";
            log.warn("Article sin source: {}", Transformer.toString(article));
        }

        //Si el articulo no tiene author
        if (article.author == null) {
            article.author = "No Author*";
            log.warn("Article sin author: {}", Transformer.toString(article));
        }
        final ZonedDateTime publishedAt = parse(article.publishedAt);

        //Using the builder pattern
        return new NoticiaBuilder()
                .setTitulo(article.title)
                .setFuente(article.source.name)
                .setAutor(article.author)
                .setUrl(article.url)
                .setUrlFoto(article.urlToImage)
                .setResumen(article.description)
                .setContenido(article.content)
                .setFecha(publishedAt)
                .createNoticia();

    }
}
