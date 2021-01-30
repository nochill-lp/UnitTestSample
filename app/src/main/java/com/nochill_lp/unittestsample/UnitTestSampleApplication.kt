package com.nochill_lp.unittestsample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
* NTT Data Italia S.p.A.
*
* @author Luca Pollastri - luca.pollastri@nttdata.com
* @version 1.0
*
*/

class UnitTestSampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            androidLogger()
            // declare used Android context
            androidContext(this@UnitTestSampleApplication)

            modules(appModule)
        }
    }
}