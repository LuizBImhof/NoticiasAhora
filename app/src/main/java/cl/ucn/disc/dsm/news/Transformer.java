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

package cl.ucn.disc.dsm.news;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import cl.ucn.disc.dsm.news.model.Noticia;

/**
 * Transformador de T a Noticia.
 *
 * @author Diego Urrutia-Astorga.
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
     *
     * @param noticiaTransformer a usar para la conversion
     */
    public Transformer(NoticiaTransformer<T> noticiaTransformer) {
        Objects.requireNonNull(noticiaTransformer, "Se requiere un transformador de noticia");
        this.noticiaTransformer = noticiaTransformer;
    }

    /**
     * Transforma en String un objeto t mostrando sus atributos.
     *
     * @param t   to convert.
     * @param <T> type of t.
     * @return the object in string format.
     */
    public static <T> String toString(final T t) {
        return ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE);
    }


    /**
     * Transforma una {@link Collection} de T en una {@link List} de noticia
     * se usa collection pois puede ser cualquier lista
     *
     * @param collection fuente de T
     * @return la lista de noticias
     */
    public List<Noticia> transform(final Collection<T> collection) {

        //no se permite null
        Objects.requireNonNull(collection, "No se permite una collection null");

        //Micro optimizacion: se inicializa con el tamando del collection
        final List<Noticia> noticias = new ArrayList<>(collection.size());

        for (final T t : collection) {

            try {

                // Transformo y agrego a la lista de noticias el t.
                noticias.add(this.noticiaTransformer.transform(t));
            } catch (NoticiaTransformerException e) {
                // .. si se produce una exception, despliego un warning y se omite.
                log.warn("Article skipped: {}", e.getMessage(), e);
            }
        }

        return noticias;
    }

    /**
     * Responsable de transformar una T en una {@link Noticia}
     *
     * @param <T> generico a usar como base
     */
    public interface NoticiaTransformer<T> {

        /**
         * @param t a transformar
         * @return la noticia a partir de t
         */
        Noticia transform(T t);

    }


    /**
     * La exception en caso de algun error en la transformacion
     */
    public static final class NoticiaTransformerException extends RuntimeException {

        /**
         * @param message
         * @see RuntimeException
         */
        public NoticiaTransformerException(final String message) {
            super(message);
        }

        /**
         * @param message
         * @param cause
         * @see RuntimeException
         */
        public NoticiaTransformerException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
