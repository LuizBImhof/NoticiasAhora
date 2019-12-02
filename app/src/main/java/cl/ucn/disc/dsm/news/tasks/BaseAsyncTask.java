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

import android.os.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General Task.
 *
 * @param <Params> to use to run in background.
 * @param <T>      to output as result.
 */
public abstract class BaseAsyncTask<Params, T> extends AsyncTask<Params, String, Resource<T>> {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(BaseAsyncTask.class);

    /**
     * The Listener
     */
    private final Resource.ResourceListener<T> listener;

    /**
     * @param listener to use.
     */
    public BaseAsyncTask(final Resource.ResourceListener<T> listener) {
        this.listener = listener;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     */
    @Override
    protected void onPreExecute() {
        // Maybe the listener can be null.
        if (this.listener != null) {
            this.listener.onStarting();
        }
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     */
    @Override
    protected void onProgressUpdate(final String... values) {
        // Maybe the listener can be null.
        if (this.listener != null) {
            this.listener.onProgress(values[0]);
        }
    }

    /**
     * Runs on the UI thread after {@link #cancel(boolean)} is invoked and doInBackground has finished.
     */
    @Override
    protected void onCancelled() {
        if (this.listener != null) {
            this.listener.onCancelled();
        }
    }

    /**
     * Runs on the UI thread after {@link #doInBackground}.
     */
    @Override
    protected void onPostExecute(final Resource<T> result) {

        if (this.listener == null) {
            log.warn("Listener null, skipping");
            return;
        }

        if (result.getStatus() == Resource.Status.SUCESS) {
            this.listener.onSuccess(result.getResource());
        } else {
            this.listener.onFailure(result.getException());
        }

    }
}
