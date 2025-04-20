package edu.iesam.gametracker.features.setting

import edu.iesam.gametracker.features.setting.data.local.SettingDbLocalDataSource
import edu.iesam.gametracker.features.setting.data.remote.SettingFirebaseDataSource
import edu.iesam.gametracker.features.setting.domain.Developer
import edu.iesam.gametracker.features.setting.domain.Resource
import edu.iesam.gametracker.features.setting.domain.SettingRepository
import org.koin.core.annotation.Single

@Single
class SettingDataRepository(
    private val remote: SettingFirebaseDataSource,
    private val local: SettingDbLocalDataSource
) : SettingRepository {
    override suspend fun getDevelopers(): Result<List<Developer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResources(): Result<List<Resource>> {
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