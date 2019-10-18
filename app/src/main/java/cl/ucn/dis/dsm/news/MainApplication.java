/*
 * Copyright (c) 2019. Luiz Artur Boing Imhof
 */

package cl.ucn.dis.dsm.news;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main application
 * @author Luiz Artur Boing Imhof
 */

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraToast(resText = R.string.crash_toast_message)
@AcraMailSender(mailTo = "luiz.imhof@gmail.com")

public final class MainApplication extends Application {

    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

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
