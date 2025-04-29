package edu.iesam.gametracker.features.setting.data.resources.local

import edu.iesam.gametracker.features.setting.domain.resources.Resources

fun Resources.toEntity(): ResourcesEntity {
    return ResourcesEntity(
        this.id,
        this.image,
        this.name,
        this.urlWeb
    )
}

fun ResourcesEntity.toDomain(): Resources {
    return Resources(
        this.id,
        this.image,
        this.name,
        this.urlWeb
    )
}