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

package cl.ucn.dis.dsm.news.newsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

import cl.ucn.dis.dsm.news.Transformer;
import cl.ucn.dis.dsm.news.model.Noticia;

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
     * @see Transformer
     */
    @Override
    public Noticia transform(final Article article) {

        // The Noticia.
        ZonedDateTime publishedAt;
        try {
            publishedAt = ZonedDateTime.parse(article.publishedAt);
        } catch (NullPointerException ex) {
            throw new Transformer.NoticiaTransformerException("La noticia debe tener al menos descripcion y fecha", ex);
        }catch (DateTimeParseException ex){
            throw new Transformer.NoticiaTransformerException("No se pude parsear la fecha", ex);
        }

        final String descripcion = article.description;
        try {
            descripcion.equals(null);
        }catch (NullPointerException ex){
            throw new Transformer.NoticiaTransformerException("La noticia debe tener al menos descripcion y fecha", ex);
        }



        String titulo = article.title;
        try {
            titulo.equals(null);
        } catch (NullPointerException e) {
            titulo = "No se puede buscar el titulo del Article";
        }

        String nombreFuente = null;
        try {
            nombreFuente = article.source.name;
        } catch (NullPointerException e) {
            nombreFuente = "No se puede buscar la fuente del Article";
        }

        String url = article.url;
        try {
            url.equals(null);
        } catch (NullPointerException e) {
            url = "No se puede buscar la url del Article";
        }

        String imagenUrl = article.urlToImage;
        try {
            imagenUrl.equals(null);
        } catch (NullPointerException e) {
            imagenUrl = "No se puede buscar la imagen del Article";
        }
        String contenido = article.content;
        try {
            contenido.equals(null);
        } catch (NullPointerException e) {
            contenido = "No se puede buscar el contenido del Article";
        }

        String autor = article.author;
        try {
            autor.equals(null);
        } catch (NullPointerException e) {
            autor = "No se puede buscar el autor del Article";
        }

        log.debug("Fecha de la noticia {}", publishedAt.toString());
        log.debug("Descripcion de la noticia {}", descripcion);

        log.debug("Titulo de la noticia {}", titulo);
        log.debug("Fuente de la noticia {}", nombreFuente);
        log.debug("Url de la noticia {}", url);
        log.debug("ImagenUrl de la noticia {}", imagenUrl);
        log.debug("Contenido de la noticia {}", contenido);
        log.debug("Autor de la noticia {}", autor);


        try {
            return new Noticia(
                    titulo,
                    nombreFuente,
                    url,
                    imagenUrl,
                    descripcion,
                    contenido,
                    publishedAt,
                    autor
            );
        } catch (NullPointerException ex) {
            throw new Transformer.NoticiaTransformerException("Fecha o descripción null", ex);
        }


    }
}
