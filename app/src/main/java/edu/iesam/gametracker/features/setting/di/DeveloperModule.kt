package edu.iesam.gametracker.features.setting.di

import com.google.firebase.firestore.FirebaseFirestore
import edu.iesam.gametracker.app.data.local.db.GameTrackerDataBase
import edu.iesam.gametracker.features.setting.data.developer.local.DeveloperDao
import edu.iesam.gametracker.features.setting.data.developer.remote.DeveloperFirebaseDataSource
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class DeveloperModule {

    @Single
    fun provideDeveloperFirebaseRemoteDataSource(db: FirebaseFirestore): DeveloperFirebaseDataSource {
        return DeveloperFirebaseDataSource(db)
    }


    @Single
    fun provideDeveloperDao(db: GameTrackerDataBase): DeveloperDao {
        return db.developerDao()
    }
}