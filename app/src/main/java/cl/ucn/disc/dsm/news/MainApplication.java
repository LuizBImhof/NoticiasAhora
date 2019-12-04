/*
 * Copyright 2019 Diego Urrutia-Astorga.
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

package cl.ucn.disc.dsm.news;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main Application.
 *
 * @author Diego Urrutia-Astorga
 * @author Luiz Artur Boing Imhof
 * @version 2019.
 */
@AcraCore(buildConfigClass = BuildConfig.class)
@AcraToast(resText = R.string.crash_toast_message)

public final class MainApplication extends Application {

    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        log.atDebug().log("Initializing Fresco");
        Fresco.initialize(this);
        log.atDebug().log("... Fresco initialized");
    }

    /**
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        log.atDebug().log("Initializing ACRA");
        ACRA.init(this);
        log.atDebug().log("...ACRA initialized");
    }
}
