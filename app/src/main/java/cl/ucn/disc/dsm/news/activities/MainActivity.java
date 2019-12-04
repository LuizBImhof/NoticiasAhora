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

package cl.ucn.disc.dsm.news.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ucn.disc.dsm.news.R;
import cl.ucn.disc.dsm.news.adapters.NoticiaAdapter;
import cl.ucn.disc.dsm.news.adapters.RecyclerNoticiaAdapter;
import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService;
import cl.ucn.disc.dsm.news.tasks.LoadNoticiasTask;
import cl.ucn.disc.dsm.news.tasks.Resource.ResourceListener;

import java.util.List;

/**
 * MainActiviy using ListActivity to show the list on Noticias.
 *
 * @author Diego Urrutia Astorga.
 */
public class MainActivity extends AppCompatActivity implements ResourceListener<List<Noticia>> {

    /**
     * The logger
     */
    Logger log = LoggerFactory.getLogger(MainActivity.class);
    /**
     * The Adapter de Noticias.
     */
    private RecyclerNoticiaAdapter noticiaAdapter;

    /**
     * The progress dialog
     */
    private ProgressDialog progressDialog;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The layout
        this.setContentView(R.layout.activitylist_noticias);

        // "Loading .."
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Loading ..");

    }

    /**
     * Called after {@link #onCreate} or after {@link #onRestart} when the activity had been stopped.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Run in the backgrounds

        new LoadNoticiasTask(this).execute(
                NewsApiService.Category.science,
                NewsApiService.Category.business,
                NewsApiService.Category.technology
        );


        //new LoadNoticiasTask(this).execute();

    }

    /**
     * In the beginning
     */
    public void onStarting() {
        this.progressDialog.show();
    }

    /**
     * @param message the progress.
     */
    public void onProgress(String message) {
        this.progressDialog.setMessage("Loading " + message + " ..");
    }

    /**
     * @param noticias to use as output.
     */
    public void onSuccess(List<Noticia> noticias) {

        // Hide the dialog
        this.progressDialog.hide();

        // Create the recYclerView
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        // The recyclerView Adapter
        noticiaAdapter = new RecyclerNoticiaAdapter(this, noticias);

        recyclerView.setAdapter(noticiaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * If was cancelled
     */
    public void onCancelled() {
        this.progressDialog.hide();
    }

    /**
     * @param ex error to use.
     */
    public void onFailure(Exception ex) {
        this.progressDialog.hide();
    }

}
