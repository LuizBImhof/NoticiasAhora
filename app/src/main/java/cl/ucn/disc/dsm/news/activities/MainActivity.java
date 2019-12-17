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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ucn.disc.dsm.news.activities.model.NoticiasViewModel;
import cl.ucn.disc.dsm.news.adapters.RecyclerNoticiaAdapter;
import cl.ucn.disc.dsm.news.databinding.ActivitylistNoticiasBinding;

/**
 * MainActiviy using ViewModel, recycler view and observer to show the list on Noticias.
 *
 * @author Diego Urrutia Astorga.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The logger
     */
    Logger log = LoggerFactory.getLogger(MainActivity.class);
    /**
     * The Adapter de Noticias.
     */
    private RecyclerNoticiaAdapter noticiaAdapter;

    /**
     * The ViewModel
     */
    private NoticiasViewModel noticiasViewModel;

    /**
     * The binding
     */
    private ActivitylistNoticiasBinding binding;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivitylistNoticiasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.noticiaAdapter = new RecyclerNoticiaAdapter();

        // The list of Noticias
        {
            this.binding.rvNoticias.setLayoutManager(new LinearLayoutManager(this));
            this.binding.rvNoticias.setAdapter(this.noticiaAdapter);
        }
        // The view-model
        this.noticiasViewModel = new ViewModelProvider(this).get(NoticiasViewModel.class);

        // The refresh
        {
            this.binding.swlRefresh.setOnRefreshListener(() -> {
                this.noticiasViewModel.refresh();
            });
        }
        // The observe
        this.noticiasViewModel.getNoticias().observe(this, noticias -> {

            log.debug("Noticias: {}.", noticias.size());

            // Update the adapter
            this.noticiaAdapter.setNoticias(noticias);
            // Hide the loading
            this.binding.swlRefresh.setRefreshing(false);

        });

    }


}
