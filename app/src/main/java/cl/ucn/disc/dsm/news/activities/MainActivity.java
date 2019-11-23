/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.disc.dsm.news.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.news.adapters.NoticiaAdapter;
import cl.ucn.disc.dsm.news.model.Noticia;
import cl.ucn.disc.dsm.news.newsapi.NewsApiService;

public class MainActivity extends ListActivity {

    /**
     * El Adapter de noticias
     */
    private NoticiaAdapter noticiaAdapter;

    private final NewsApiService newsApiService = new NewsApiService();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.noticiaAdapter = new NoticiaAdapter(this);

        this.setListAdapter(this.noticiaAdapter);
        GetNoticiaTask getNoticiaTask = new GetNoticiaTask();

        getNoticiaTask.execute(NewsApiService.Category.general, NewsApiService.Category.sports, NewsApiService.Category.technology);

    }

    public class GetNoticiaTask extends AsyncTask<NewsApiService.Category,Void, List<Noticia>>{

        public GetNoticiaTask() {
        }

        /**
         * <p>Applications should preferably override {@link #onCancelled(Object)}.
         * This method is invoked by the default implementation of
         * {@link #onCancelled(Object)}.
         * The default version does nothing.</p>
         *
         * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
         * {@link #doInBackground(Object[])} has finished.</p>
         *
         * @see #onCancelled(Object)
         * @see #cancel(boolean)
         * @see #isCancelled()
         */


        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.
         * To better support testing frameworks, it is recommended that this be
         * written to tolerate direct execution as part of the execute() call.
         * The default version does nothing.</p>
         *
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param noticias The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled()
         */
        @Override
        protected void onPostExecute(List<Noticia> noticias) {
            noticiaAdapter.setNoticias(noticias);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param categories The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Noticia> doInBackground(NewsApiService.Category... categories) {
            List<Noticia> noticias = new ArrayList<>();

            for (NewsApiService.Category categoria: categories) {
                if (isCancelled()) {
                    return null;
                }
                noticias.addAll(newsApiService.getNoticias(categoria,10));
            }
            return  noticias;
        }
    }
}
