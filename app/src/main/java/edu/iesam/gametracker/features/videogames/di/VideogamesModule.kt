package edu.iesam.gametracker.features.videogames.di

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
}