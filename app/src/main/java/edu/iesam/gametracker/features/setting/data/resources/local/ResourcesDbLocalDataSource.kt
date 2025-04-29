package edu.iesam.gametracker.features.setting.data.resources.local

import edu.iesam.gametracker.features.setting.domain.resources.Resources
import org.koin.core.annotation.Single

@Single
class ResourcesDbLocalDataSource(private val resourcesDao: ResourcesDao) {

    suspend fun findAllResources(): Result<List<Resources>> {
        val resources = resourcesDao.getAll()
        return if (resources.isEmpty()) {
            Result.success(emptyList())
        } else {
            Result.success(resources.map { it.toDomain() })
        }
    }

    suspend fun saveResources(resources: List<Resources>) {
        val resourcesList = resources.map {
            it.toEntity()
        }
        resourcesDao.saveResources(*resourcesList.toTypedArray())
    }
}