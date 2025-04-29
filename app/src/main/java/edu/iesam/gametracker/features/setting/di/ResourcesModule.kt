package edu.iesam.gametracker.features.setting.di

import com.google.firebase.firestore.FirebaseFirestore
import edu.iesam.gametracker.app.data.local.db.GameTrackerDataBase
import edu.iesam.gametracker.features.setting.data.resources.local.ResourcesDao
import edu.iesam.gametracker.features.setting.data.resources.remote.ResourcesFirebaseDataSource
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class ResourcesModule {

    @Single
    fun provideResourcesFirebaseRemoteDataSource(db: FirebaseFirestore): ResourcesFirebaseDataSource {
        return ResourcesFirebaseDataSource(db)
    }

    @Single
    fun provideResourcesDao(db: GameTrackerDataBase): ResourcesDao {
        return db.resourcesDao()
    }
}