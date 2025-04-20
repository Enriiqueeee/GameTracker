package edu.iesam.gametracker.features.setting.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import edu.iesam.gametracker.features.setting.domain.Resource
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class SettingFirebaseDataSource(private val firestore: FirebaseFirestore) {

    suspend fun getResources(): Result<List<Resource>> {
        val resources = firestore.collection("resources")
            .get()
            .await()
            .map{
                it.toObject(ResourcesFirebaseModel::class.java).toModel()
            }
        return Result.success(resources)
    }
}