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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utils.
 */
public final class ModelUtils {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ModelUtils.class);

    /**
     * Mix the Noticias (removing duplicates + sorting by date).
     *
     * @param oldNoticias to mix.
     * @param newNoticias to mix.
     * @return the new List of Noticia.
     */
    public static List<Noticia> merge(final List<Noticia> oldNoticias, final List<Noticia> newNoticias) {

        // The result
        final List<Noticia> noticias = new ArrayList<>();

        // Mix A
        if (oldNoticias != null) {
            noticias.addAll(oldNoticias);
            log.debug("Noticias Old: {}.", oldNoticias.size());
        } else {
            log.debug("Noticias Old: Empty.");
        }

        // Mix B
        if (newNoticias != null) {
            noticias.addAll(newNoticias);
            log.debug("Noticias New: {}.", newNoticias.size());
        } else {
            log.debug("Noticias New: Empty.");
        }

        log.debug("Noticias Sum: {}.", noticias.size());

        // Filter the news
        final List<Noticia> filtered = noticias.stream()
                // FIXED: Remove duplicated.
                .filter(distintByKey(Noticia::getId))
                // FIXED: Sort by date.
                .sorted((n1, n2) -> n2.getFecha().compareTo(n1.getFecha()))
                .collect(Collectors.toList());

        log.debug("Noticias Mix: {}.", filtered.size());

        return filtered;

    }

    /**
     * @param oldNoticias to compare.
     * @param newNoticias to compare.
     * @return the new List of Noticia.
     */
    public static List<Noticia> subtraction(final List<Noticia> oldNoticias, final List<Noticia> newNoticias) {

        if (oldNoticias == null) {
            return newNoticias;
        }

        // The Map
        final Map<Long, Noticia> map = new HashMap<>();

        // The result
        final List<Noticia> result = new ArrayList<>();

        // Adding to the map
        for (final Noticia noticia : oldNoticias) {
            map.put(noticia.getId(), noticia);
        }

        // Only the not found key
        for (final Noticia noticia : newNoticias) {
            if (!map.containsKey(noticia.getId())) {
                result.add(noticia);
            }
        }

        return result;

    }


    /**
     * The Predicator to filter.
     */
    private static <T> Predicate<T> distintByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
