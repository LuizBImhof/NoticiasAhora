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

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import cl.ucn.disc.dsm.news.activities.model.NoticiasDatabaseRepository.NoticiaDAO;
import cl.ucn.disc.dsm.news.model.Noticia;

/**
 * The database.
 */
@Database(entities = {Noticia.class}, version = 1, exportSchema = true)
@TypeConverters({NoticiasDatabaseRepository.ZonedDateTimeConverter.class})
public abstract class NoticiasDatabase extends RoomDatabase {

    /**
     * The instance (singleton).
     */
    private static volatile NoticiasDatabase INSTANCE;

    /**
     * Return the instance of {@link NoticiasDatabase}.
     *
     * @param application to use as source.
     * @return the {@link NoticiasDatabase}.
     */
    public static NoticiasDatabase getInstance(final Application application) {

        if (INSTANCE == null) {
            synchronized (NoticiasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(application,
                                    NoticiasDatabase.class,
                                    "noticias.db")
                            // .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;

    }

    /**
     * @return the {@link NoticiaDAO}.
     */
    public abstract NoticiaDAO getNoticiaDAO();


}
