package edu.iesam.gametracker.features.videogames.di

import edu.iesam.gametracker.app.data.local.db.GameTrackerDataBase
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesDao
import edu.iesam.gametracker.features.videogames.data.remote.VideogamesService
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit

@Module
@ComponentScan
class VideogamesModule {

    @Single
    fun provideRetrofitService(retrofit: Retrofit): VideogamesService{
        return retrofit.create(VideogamesService::class.java)
    }


    @Single
    fun provideVideogameDao(db: GameTrackerDataBase): VideogamesDao {
        return db.videogamesDao()
    }
}