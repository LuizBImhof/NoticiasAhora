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

/**
 * The result of AsyncTask.
 *
 * @param <T>
 */
public final class Resource<T> {

    /**
     * The data
     */
    private T resource;

    /**
     * The Exception
     */
    private Exception exception;

    /**
     * The Status
     */
    private Status status;

    /**
     * All ok.
     *
     * @param data to use.
     */
    public Resource(final T data) {
        this.resource = data;
        this.status = Status.SUCESS;
    }

    /**
     * Error!
     *
     * @param exception detected.
     */
    public Resource(final Exception exception) {
        this.exception = exception;
        this.status = Status.ERROR;
    }

    /**
     * @return The status.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @return The data.
     */
    public T getResource() {
        return this.resource;
    }

    /**
     * @return The exception.
     */
    public Exception getException() {
        return this.exception;
    }

    /**
     * The Status of the Resource.
     */
    public enum Status {
        SUCESS,
        ERROR
    }

    /**
     * Listener of changes in the task of get a Resource.
     *
     * @param <T> resource to get.
     */
    public interface ResourceListener<T> {

        /**
         * In the beginning
         */
        void onStarting();

        /**
         * @param message the progress.
         */
        void onProgress(String message);

        /**
         * @param t to use as output.
         */
        void onSuccess(T t);

        /**
         * If was cancelled
         */
        void onCancelled();

        /**
         * @param ex error to use.
         */
        void onFailure(Exception ex);

    }

}
