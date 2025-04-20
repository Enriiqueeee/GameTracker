package edu.iesam.gametracker.features.setting.domain.developer

import org.koin.core.annotation.Single

@Single
class GetDeveloperUseCase(private val settingRepository: DeveloperRepository) {
    suspend operator fun invoke(): Result<List<Developer>> {
        return settingRepository.getDevelopers()
    }
}