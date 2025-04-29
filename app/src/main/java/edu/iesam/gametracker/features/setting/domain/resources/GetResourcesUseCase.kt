package edu.iesam.gametracker.features.setting.domain.resources

import org.koin.core.annotation.Single

@Single
class GetResourcesUseCase(private val resourcesRepository: ResourcesRepository) {

    suspend operator fun invoke(): Result<List<Resources>> {
        return resourcesRepository.getResources()
    }

}