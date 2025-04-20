package edu.iesam.gametracker.features.setting.domain

interface SettingRepository {
    suspend fun getDevelopers(): Result<List<Developer>>

    suspend fun getResources(): Result<List<Resource>>
}