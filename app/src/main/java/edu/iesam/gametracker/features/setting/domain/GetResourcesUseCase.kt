package edu.iesam.gametracker.features.setting.domain

import org.koin.core.annotation.Single

@Single
class GetResourcesUseCase(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(): Result<List<Resource>> {
        return settingRepository.getResources()
    }
}