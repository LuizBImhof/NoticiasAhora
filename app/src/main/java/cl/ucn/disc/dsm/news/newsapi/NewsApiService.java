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

package cl.ucn.disc.dsm.news.newsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.news.Transformer;
import cl.ucn.disc.dsm.news.model.Noticia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Clase que entrega el servicio de las NewsAPI.
 *
 * @author Diego Urrutia-Astorga.
 */


public final class NewsApiService {
    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(NewsApiService.class);

    /**
     * The Api Key
     */
    private static final String API_KEY = "65edaab846ff476c839ad860cb2dbd59";

    /**
     * El conversor de List de Article a List de Noticia.
     */
    private static final Transformer<Article> TRANSFORMER = new Transformer<>(new ArticleNoticiaTransformer());

    /**
     * The NewsAPI
     */
    private final NewsAPI newsAPI;

    /**
     * Constructor que inicializa las NewsAPI via retrofit.
     */
    public NewsApiService() {

        // https://futurestud.io/tutorials/retrofit-getting-started-and-android-client
        this.newsAPI = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPI.class);
    }

    /**
     * @return la {@link List} de {@link Noticia}.
     */
    public List<Noticia> getNoticias(Category category, int pageSize) {

        try {

            // Obtengo las noticias de technology por defecto
            final Response<NewsResult> response = newsAPI
                    .getTopHeadlines(category.toString(),pageSize)
                    .execute();

            // Si la respuesta fue exitosa
            if (response.isSuccessful()) {

                // Obtengo el newsResult del body
                final NewsResult newsResult = response.body();
                if (newsResult != null) {
                    return TRANSFORMER.transform(newsResult.articles);
                }

                throw new NewsAPIException("NewsResult fue null");
            }

            log.error("Error: {}", response.errorBody().toString());
            throw new NewsAPIException("TopHeadlines no exitoso, error: " + response.code());

        } catch (final IOException ex) {
            log.error("Error", ex);
            throw new NewsAPIException("Erro al obtener los Articles", ex);
        }

    }


    /**
     * Categorias del servicio.
     */
    public enum Category {
        business,
        entertainment,
        general,
        health,
        science,
        sports,
        technology
    }

    /**
     * API que provee NewsAPI.org
     */
    public interface NewsAPI {

        /**
         * @param category a utilizar como filtro.
         * @return the Call of {@link NewsResult}.
         */
        @Headers({"X-Api-Key: " + API_KEY})
        @GET("top-headlines")
        Call<NewsResult> getTopHeadlines(@Query("category") final String category,
                                         @Query("pageSize") final int pageSize);

        /**
         *Uses the Everything endpoint that let you choose the language
         * @param category es usado para pesquisar pos palavras claves en la API (en getEverything no se usa categoria,
         *                 pero se puede selecionar idiomas
         * @param pageSize
         * @param language
         * @return
         */
        @Headers({"X-Api-Key: " + API_KEY})
        @GET("everything")
        Call<NewsResult> getEveryThing(@Query("q") final String category,
                                       @Query("pageSize") final int pageSize,
                                       @Query("language") final String language);
    }

    /**
     * The Exception.
     */
    public static final class NewsAPIException extends RuntimeException {

        public NewsAPIException(final String message) {
            super(message);
        }

        public NewsAPIException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

}
