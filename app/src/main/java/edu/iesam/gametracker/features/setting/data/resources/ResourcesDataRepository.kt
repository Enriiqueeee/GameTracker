package edu.iesam.gametracker.features.setting.data.resources

import edu.iesam.gametracker.features.setting.data.resources.local.ResourcesDbLocalDataSource
import edu.iesam.gametracker.features.setting.data.resources.remote.ResourcesFirebaseDataSource
import edu.iesam.gametracker.features.setting.domain.resources.Resources
import edu.iesam.gametracker.features.setting.domain.resources.ResourcesRepository
import org.koin.core.annotation.Single

@Single
class ResourcesDataRepository(
    private val remote: ResourcesFirebaseDataSource,
    private val local: ResourcesDbLocalDataSource
) : ResourcesRepository {

    override suspend fun getResources(): Result<List<Resources>> {
        val resourcesLocal = local.findAllResources()
        if (resourcesLocal.isSuccess) {
            val localResources = resourcesLocal.getOrNull()
            if (!localResources.isNullOrEmpty()) {
                return Result.success(localResources)
            }
        }
        val resourcesFromRemote = remote.getResources()
        return resourcesFromRemote.onSuccess {
            local.saveResources(it)
        }
    }

}