package edu.iesam.gametracker.features.setting.data.local

import edu.iesam.gametracker.features.setting.domain.Developer
import edu.iesam.gametracker.features.setting.domain.Resource

fun Resource.toEntity(): ResourceEntity {
    return ResourceEntity(
        this.id,
        this.title,
        this.urlResource
    )
}

fun ResourceEntity.toDomain(): Resource {
    return Resource(
        this.id,
        this.title,
        this.urlResource
    )
}

fun Developer.toEntity(): DeveloperEntity {
    return DeveloperEntity(
        this.id,
        this.name,
        this.avatar
    )
}

fun DeveloperEntity.toDomain(): Developer {
    return Developer(
        this.id,
        this.name,
        this.avatar
    )
}