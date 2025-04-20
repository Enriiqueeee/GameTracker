package edu.iesam.gametracker.features.setting.data.local

import edu.iesam.gametracker.features.setting.domain.Resource
import org.koin.core.annotation.Single

@Single
class SettingDbLocalDataSource(private val settingDao: SettingDao) {

    suspend fun findAllResources(): Result<List<Resource>> {
        val resources = settingDao.findAllResources()
        return if (resources.isEmpty()) {
            Result.success(emptyList())
        } else {
            Result.success(resources.map { it.toDomain() })
        }
    }

    suspend fun saveResources(resources: List<Resource>) {
        val resourceList = resources.map {
            it.toEntity()
        }
    }
}