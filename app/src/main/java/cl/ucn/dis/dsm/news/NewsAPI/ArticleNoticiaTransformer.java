/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.dis.dsm.news.NewsAPI;

import android.service.notification.ZenPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

import cl.ucn.dis.dsm.news.Transformer;
import cl.ucn.dis.dsm.news.model.Noticia;

/**
 * Transformacion de un {@link Article} para un {@link Noticia}
 *
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
