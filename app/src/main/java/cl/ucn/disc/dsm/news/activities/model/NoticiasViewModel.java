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

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService;
import cl.ucn.disc.dsm.news.tasks.LoadNoticiasTask;
import cl.ucn.disc.dsm.news.tasks.Resource;

/**
 * The Noticias ViewModel.
 */
public class NoticiasViewModel extends ViewModel implements Resource.ResourceListener<List<Noticia>> {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(NoticiasViewModel.class);

    /**
     * The list of Noticias.
     */
    private final MutableLiveData<List<Noticia>> theNoticias = new MutableLiveData<>();

    /**
     * Asyntask to load the news
     */
    private LoadNoticiasTask loadNoticiasTask;

    /**
     *
     */
    public NoticiasViewModel() {
        super();
    }

    /**
     * @return the LiveData of Noticias.
     */
    public LiveData<List<Noticia>> getNoticias() {
        return this.theNoticias;
    }

    /**
     * Get the latest news from NewsAPI
     */
    public void loadNoticias(NewsApiService.Category... categories) {

        if (loadNoticiasTask == null) {

            log.debug("Creating new AsynTask to load Noticias ..");
            this.loadNoticiasTask = new LoadNoticiasTask(this);

            // Execute!
            this.loadNoticiasTask.execute(categories);

        } else {
            log.debug("Already running a task, skipping.");
        }

    }

    /**
     * On start!
     */
    @Override
    public void onStarting() {
        log.debug("Starting the task ..");
    }

    /**
     * @param message of progress.
     */
    @Override
    public void onProgress(final String message) {
        log.debug("Trying to get Noticias of type: {} ..", message);
    }

    /**
     * @param theResult to use as output.
     */
    @Override
    public void onSuccess(final List<Noticia> theResult) {

        log.debug("Success, Total of Noticias founded: {}", theResult.size());

        // Need to do in the main thread.
        theNoticias.setValue(theResult);
        this.loadNoticiasTask = null;
    }

    /**
     * If was cancelled
     */
    @Override
    public void onCancelled() {

        log.debug("Cancelled the load !");
        this.loadNoticiasTask = null;
    }

    /**
     * @param e error to use.
     */
    @Override
    public void onFailure(final Exception e) {

        log.error("onFailure!", e);
        this.loadNoticiasTask = null;
    }

}
