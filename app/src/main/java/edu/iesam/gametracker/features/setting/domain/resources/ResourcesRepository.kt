package edu.iesam.gametracker.features.setting.domain.resources

interface ResourcesRepository {
    suspend fun getResources(): Result<List<Resources>>
}