package gr.jvoyatz.sportspot

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class SportSpotApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(TimberDebugTree())
        }
    }

}