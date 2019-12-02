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

package cl.ucn.disc.dsm.news.tasks;

import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService.NewsAPIException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task to load the Noticias.
 *
 * @author Diego Urrutia-Astorga.
 */
public final class LoadNoticiasTask extends BaseAsyncTask<NewsApiService.Category, List<Noticia>> {

    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(LoadNoticiasTask.class);

    /**
     * The {@link NewsApiService} to get the news from internet.
     */
    private static final NewsApiService NEWS_API_SERVICE = new NewsApiService();

    /**
     * How many Noticias we get
     */
    private static final int SIZE = 50;

    /**
     * @param listener to use.
     */
    public LoadNoticiasTask(final Resource.ResourceListener<List<Noticia>> listener) {
        super(listener);
    }

    /**
     * Override this method to perform a computation on a background thread.
     */
    @Override
    protected Resource<List<Noticia>> doInBackground(final NewsApiService.Category... categories) {

        // No category, get the topnews
        return categories.length != 0 ? getNoticias(categories) : getTopNoticias();

    }

    /**
     * @return the {@link Resource} of {@link List} of {@link Noticia}.
     */
    private Resource<List<Noticia>> getTopNoticias() {

        try {

            // The Resource.
            return new Resource<>(NEWS_API_SERVICE.getNoticias(SIZE));

        } catch (final NewsAPIException ex) {

            log.error("Error getting the Noticias", ex);

            // The error
            return new Resource<>(ex);

        }

    }

    /**
     * Get the Noticas filtered by {@link cl.ucn.disc.dsm.news.newsapi.NewsApiService.Category}.
     *
     * @param categories to get.
     * @return the Resource of List of Noticia.
     */
    private Resource<List<Noticia>> getNoticias(final NewsApiService.Category... categories) {

        // Listado de TODAS las noticias de las categorias
        final List<Noticia> allNoticias = new ArrayList<>(SIZE * categories.length);

        // Itero sobre cada categoria
        for (NewsApiService.Category category : categories) {

            // Publish the current category
            this.publishProgress(String.valueOf(category));

            // Si fue cancelada, exit!
            if (this.isCancelled()) {
                return null;
            }

            // Agrego las noticias parciales.
            try {

                final List<Noticia> noticias = NEWS_API_SERVICE.getNoticias(category, SIZE);
                log.debug("Founded {} Noticias of type {}.", noticias.size(), category);

                allNoticias.addAll(noticias);

            } catch (final NewsAPIException ex) {
                log.error("Error getting the Noticias", ex);

                // Return the error
                return new Resource<>(ex);
            }
        }

        return new Resource<>(allNoticias);

    }

}
