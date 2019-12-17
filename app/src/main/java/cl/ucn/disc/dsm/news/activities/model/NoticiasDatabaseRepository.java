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

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;

import cl.ucn.disc.dsm.news.model.Noticia;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;
import org.threeten.bp.ZonedDateTime;

/**
 * Database repository.
 */
public final class NoticiasDatabaseRepository {

    /**
     * The Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(NoticiasDatabaseRepository.class);

    /**
     * The database.
     */
    private final NoticiasDatabase noticiaDatabase;

    /**
     * Constructor
     */
    public NoticiasDatabaseRepository(final Application application) {

        // TODO: https://gist.github.com/adigunhammedolalekan/5b04cd90ff8ba76a5f13484444a175f9
        // TODO: https://medium.com/@guendouz/room-livedata-and-recyclerview-d8e96fb31dfe

        this.noticiaDatabase = NoticiasDatabase.getInstance(application);

    }

    /**
     * @return the List of Noticia inside of LiveData.
     */
    public LiveData<List<Noticia>> getNoticias() {
        return this.noticiaDatabase
                .getNoticiaDAO()
                .findAll();
    }

    /**
     * @param noticias to save into the database.
     */
    public void saveNoticias(final List<Noticia> noticias) {
        this.noticiaDatabase.getNoticiaDAO().saveAll(noticias);
    }

    /**
     * The DAO of Noticia
     */
    @Dao
    public interface NoticiaDAO {

        /**
         * @param noticias to save in the backend.
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void saveAll(List<Noticia> noticias);

        /**
         * @param noticia to save.
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void save(Noticia noticia);

        /**
         * @return the list of Noticia.
         */
        @Query("SELECT * FROM noticia ORDER BY fecha DESC")
        LiveData<List<Noticia>> findAll();

    }

    /**
     * The Converters
     */
    public static class ZonedDateTimeConverter {

        /**
         * Long to ZonedDateTime.
         */
        @TypeConverter
        public static ZonedDateTime toZonedDateTime(final Long timestamp) {
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), Noticia.ZONE_ID);
        }

        /**
         * ZonedDateTime to Long.
         */
        @TypeConverter
        public static Long toLong(final ZonedDateTime zonedDateTime) {
            return zonedDateTime.toInstant().toEpochMilli();
        }

    }

}
