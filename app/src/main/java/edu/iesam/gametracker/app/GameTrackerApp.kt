package edu.iesam.gametracker.app

import android.app.Application
import com.google.firebase.FirebaseApp
import edu.iesam.gametracker.app.di.AppModule
import edu.iesam.gametracker.app.di.LocalModule
import edu.iesam.gametracker.app.di.RemoteModule
import edu.iesam.gametracker.features.setting.di.DeveloperModule
import edu.iesam.gametracker.features.setting.di.ResourcesModule
import edu.iesam.gametracker.features.videogames.di.VideogamesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class GameTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@GameTrackerApp)
            modules(
                AppModule().module,
                RemoteModule().module,
                LocalModule().module,
                VideogamesModule().module,
                DeveloperModule().module,
                ResourcesModule().module
            )
        }
    }
}