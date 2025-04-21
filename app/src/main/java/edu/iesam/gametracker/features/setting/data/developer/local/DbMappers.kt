package edu.iesam.gametracker.features.setting.data.developer.local

import edu.iesam.gametracker.features.setting.domain.developer.Developer

fun Developer.toEntity(): DeveloperEntity {
    return DeveloperEntity(
        this.id,
        this.name,
        this.githubUrl
    )
}

fun DeveloperEntity.toDomain(): Developer {
    return Developer(
        this.id,
        this.name,
        this.githubUrl
    )
}