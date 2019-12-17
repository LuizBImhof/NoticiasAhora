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

package com.durrutia.android;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class TheExecutor {

    /**
     * The instance.
     */
    private static volatile TheExecutor INSTANCE;

    /**
     *
     */
    private final Executor diskIO;

    /**
     *
     */
    private final Executor networkIO;

    /**
     *
     */
    private final Executor mainThread;

    /**
     *
     */
    public TheExecutor() {
        this.diskIO = Executors.newSingleThreadExecutor();
        this.networkIO = Executors.newFixedThreadPool(3);
        this.mainThread = new MainThreadExecutor();
    }

    /**
     * Return the instance of {@link TheExecutor}.
     */
    private static TheExecutor getInstance() {

        if (INSTANCE == null) {
            synchronized (TheExecutor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TheExecutor();
                }
            }
        }

        return INSTANCE;

    }

    /**
     * Execute in the Disk IO Thread.
     *
     * @param runnable to exec.
     */
    public static void execInDiskIO(final Runnable runnable) {
        getInstance().diskIO.execute(runnable);
    }

    /**
     * Execute in the Network IO Thread.
     *
     * @param runnable to exec.
     */
    public static void execInNetworkIO(final Runnable runnable) {
        getInstance().networkIO.execute(runnable);
    }

    /**
     * Execute in the MainThread.
     *
     * @param runnable to exec.
     */
    public static void execInMainThread(final Runnable runnable) {
        getInstance().mainThread.execute(runnable);
    }


    /**
     * The Main Thread Executor.
     */
    private static class MainThreadExecutor implements Executor {

        /**
         * Connection to main thread.
         */
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        /**
         * Executes the given command at some time in the future.  The command may execute in a new thread, in a pooled
         * thread, or in the calling thread, at the discretion of the {@code Executor} implementation.
         *
         * @param command the runnable task
         */
        @Override
        public void execute(final Runnable command) {
            this.mainThreadHandler.post(command);
        }

    }


}
