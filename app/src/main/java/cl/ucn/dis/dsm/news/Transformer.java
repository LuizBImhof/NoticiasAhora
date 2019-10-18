/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.dis.dsm.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import cl.ucn.dis.dsm.news.model.Noticia;

/**
 * @author Luiz Artur
 *
 */
public final class Transformer<T> {

    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(Transformer.class);

    /**
     * The transformers (transformador de noticias0)
     */
    private final NoticiaTransformer<T> noticiaTransformer;

    /**
     * The constructor
     * @param noticiaTransformer a usar para la conversion
     */
    public Transformer(NoticiaTransformer<T> noticiaTransformer) {
        Objects.requireNonNull(noticiaTransformer, "Se requiere un transformador de noticia");
        this.noticiaTransformer = noticiaTransformer;
    }

    /**
     * Transforma una {@link Collection} de T en una {@link List} de noticia
     * se usa collection pois puede ser cualquier lista
     * @param collection fuente de T
     * @return la lista de noticias
     */
    public List<Noticia> transform (final Collection<T> collection){

        //no se permite null
        Objects.requireNonNull(collection, "No se permite una collection null");

        //Micro optimizacion: se inicializa con el tamando del collection
        final List<Noticia> noticias = new ArrayList<>(collection.size());

        for (final T t:collection) {

            try {
                final Noticia noticia = this.noticiaTransformer.transform(t);
                noticias.add(noticia);
            } catch (NoticiaTransformerException  e) {
                log.warn("Article skipped: {}", e.getMessage(), e);
            }
        }

        return noticias;
    }

    /**
     *Responsable de transformar una T en una {@link Noticia}
     * @param <T> generico a usar como base
     */
    public interface NoticiaTransformer<T>{

        /**
         *
         * @param t a transformar
         * @return la noticia a partir de t
         */
        Noticia transform(T t);

    }


    /**
     * La exception en caso de algun error en la transformacion
     */
    public static final class NoticiaTransformerException extends RuntimeException{

        /**
         * @see RuntimeException
         * @param message
         */
        public NoticiaTransformerException(final String message) {
            super(message);
        }

        /**
         * @see RuntimeException
         * @param message
         * @param cause
         */
        public NoticiaTransformerException(final String message, final Throwable cause){
            super(message,cause);
        }
    }
}
