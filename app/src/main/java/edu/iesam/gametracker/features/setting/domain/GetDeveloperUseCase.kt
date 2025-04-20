package edu.iesam.gametracker.features.setting.domain

import org.koin.core.annotation.Single

@Single
class GetDeveloperUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Result<List<Developer>> {
        return settingRepository.getDevelopers()
    }
}