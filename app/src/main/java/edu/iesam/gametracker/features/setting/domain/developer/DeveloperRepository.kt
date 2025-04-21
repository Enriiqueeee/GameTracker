package edu.iesam.gametracker.features.setting.domain.developer

interface DeveloperRepository {
    suspend fun getDevelopers(): Result<List<Developer>>
}