package edu.iesam.gametracker.features.setting.data.developer.remote

import com.google.firebase.firestore.FirebaseFirestore
import edu.iesam.gametracker.features.setting.domain.developer.Developer
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class DeveloperFirebaseDataSource(private val firestore: FirebaseFirestore) {

    suspend fun getDevelopers(): Result<List<Developer>> {
        val developers = firestore.collection("developers")
            .get()
            .await()
            .map {
                it.toObject(DeveloperFirebaseModel::class.java).toModel()
            }
        return Result.success(developers)
    }


}