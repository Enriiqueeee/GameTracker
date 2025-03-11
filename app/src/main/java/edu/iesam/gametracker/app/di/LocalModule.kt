package edu.iesam.gametracker.app.di

import android.content.Context
import androidx.room.Room
import edu.iesam.gametracker.app.data.local.db.GameTrackerDataBase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class LocalModule {

    @Single
    fun provideDataBase(context: Context): GameTrackerDataBase {
        val db = Room.databaseBuilder(
            context,
            GameTrackerDataBase::class.java,
            "game-tracker-db"
        )
        db.fallbackToDestructiveMigration()
        return db.build()
    }
}