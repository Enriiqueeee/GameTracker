package edu.iesam.gametracker.features.setting.data.developer.local

import edu.iesam.gametracker.features.setting.domain.developer.Developer
import org.koin.core.annotation.Single

@Single
class DeveloperDbLocalDataSource(private val developerDao: DeveloperDao) {


    suspend fun findAllDevelopers(): Result<List<Developer>> {
        val developers = developerDao.findAllDevelopers()
        return if (developers.isEmpty()) {
            Result.success(emptyList())
        } else {
            Result.success(developers.map { it.toDomain() })
        }
    }

    suspend fun saveDevelopers(developers: List<Developer>) {
        val developerList = developers.map {
            it.toEntity()
        }
        developerDao.saveDevelopers(*developerList.toTypedArray())
    }
}