package edu.iesam.gametracker.features.setting.data.resources.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.iesam.gametracker.features.setting.domain.resources.Resources
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class ResourcesFirebaseDataSource(private val firestore: FirebaseFirestore) {

    suspend fun getResources(): Result<List<Resources>> {
        val resources = firestore.collection("resources")
            .get()
            .await()
            .map {
                it.toObject(ResourcesFirebaseModel::class.java).toModel()
            }
        return Result.success(resources)
    }
}