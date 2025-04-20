package edu.iesam.gametracker.features.setting.data.developer

import edu.iesam.gametracker.features.setting.data.developer.local.DeveloperDbLocalDataSource
import edu.iesam.gametracker.features.setting.data.developer.remote.DeveloperFirebaseDataSource
import edu.iesam.gametracker.features.setting.domain.developer.Developer
import edu.iesam.gametracker.features.setting.domain.developer.DeveloperRepository
import org.koin.core.annotation.Single

@Single
class DeveloperDataRepository(
    private val remote: DeveloperFirebaseDataSource,
    private val local: DeveloperDbLocalDataSource
) : DeveloperRepository {
    override suspend fun getDevelopers(): Result<List<Developer>> {
        val developersLocal = local.findAllDevelopers()
        if (developersLocal.isSuccess) {
            val localDevelopers = developersLocal.getOrNull()
            if (!localDevelopers.isNullOrEmpty()) {
                return Result.success(localDevelopers)
            }
        }
        val developersFromRemote = remote.getDevelopers()
        return developersFromRemote.onSuccess {
            local.saveDevelopers(it)
        }
    }

}