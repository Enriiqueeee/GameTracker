package edu.iesam.gametracker.features.setting.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.iesam.gametracker.app.data.local.db.GameTrackerDataBase
import edu.iesam.gametracker.features.setting.data.local.SettingDao
import edu.iesam.gametracker.features.setting.data.remote.SettingFirebaseDataSource
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class SettingModule {

    @Single
    fun provideFirebaseFirestore(): FirebaseFirestore =
        Firebase.firestore

    @Single
    fun provideSettingFirebaseDataSource(
        firestore: FirebaseFirestore
    ): SettingFirebaseDataSource =
        SettingFirebaseDataSource(firestore)


    @Single
    fun provideResourceDao(db: GameTrackerDataBase): SettingDao {
        return db.settingDao()
    }
}