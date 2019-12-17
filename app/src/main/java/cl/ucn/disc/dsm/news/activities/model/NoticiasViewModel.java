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

package cl.ucn.disc.dsm.news.activities.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.durrutia.android.TheExecutor;

import cl.ucn.disc.dsm.news.model.ModelUtils;
import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService.NewsAPIException;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * The ViewModel of Noticia.
 */
public final class NoticiasViewModel extends AndroidViewModel {

    /**
     * The Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(NoticiasViewModel.class);

    /**
     * Number to Noticias to get from network.
     */
    private static final int PAGE_SIZE = 90;

    /**
     * Minutes between updates
     */
    private static final int MINUTES = 15;

    /**
     * The Repository (Network).
     */
    private static final NewsApiService NEWS_API_SERVICE = new NewsApiService();

    /**
     * The Repository (Database).
     */
    private final NoticiasDatabaseRepository noticiasDatabaseRepository;

    /**
     * The Resource
     */
    private LiveData<List<Noticia>> theNoticias;

    /**
     * The Constructor
     */
    public NoticiasViewModel(final Application application) {
        super(application);

        // The repository of Noticia from Database.
        this.noticiasDatabaseRepository = new NoticiasDatabaseRepository(application);

        // Noticias from database.
        this.theNoticias = this.noticiasDatabaseRepository.getNoticias();

    }

    /**
     * @return the List of Noticia.
     */
    public LiveData<List<Noticia>> getNoticias() {
        return this.theNoticias;
    }

    /**
     * Refresh the news from repository.
     */
    public void refresh() {

        // Don't exist Noticias in the backend or is empty
        if (this.theNoticias.getValue() == null || this.theNoticias.getValue().size() == 0) {
            log.warn("No Noticias, fetching ..");
            this.fetchNoticiasFromNetworkInBackground();
            return;
        }

        // No time
        final ZonedDateTime now = ZonedDateTime.now(Noticia.ZONE_ID);
        final ZonedDateTime last = this.theNoticias.getValue().get(0).getFecha();

        log.debug("Noww: {}", now);
        log.debug("Last: {}", last);

        final long minutes = ChronoUnit.MINUTES.between(last, now);
        log.debug("Minutes: {}", minutes);

        if (minutes > MINUTES) {
            log.debug("Refreshing ..");
            this.fetchNoticiasFromNetworkInBackground();
            return;
        }

        log.debug("Fetch aborted!");

    }

    /**
     * Fetch and save the noticias.
     */
    private void fetchNoticiasFromNetworkInBackground() {

        final StopWatch stopWatch = StopWatch.createStarted();

        // Exec in background
        TheExecutor.execInNetworkIO(() -> {

            // Get from the network (in background).
            try {
                final List<Noticia> noticias = NEWS_API_SERVICE.getNoticias(PAGE_SIZE);

                // The new noticias
                final List<Noticia> newNoticias = ModelUtils.subtraction(this.theNoticias.getValue(), noticias);
                log.debug("New Noticias: {}", newNoticias.size());

                // FIXME: Send a Result in case of nothing changed.
                if (newNoticias.size() == 0) {
                    newNoticias.add(noticias.get(0));
                }

                // Insert into the database.
                this.noticiasDatabaseRepository.saveNoticias(newNoticias);

                log.debug("fetchNoticiasFromNetwork Timex: {}", stopWatch);

            } catch (final NewsAPIException ex) {
                // FIXME: Send a Result in case of error.
                log.error("Can't get the Noticias", ex);
            }

        });

    }


}

