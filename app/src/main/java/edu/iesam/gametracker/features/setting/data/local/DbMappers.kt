package edu.iesam.gametracker.features.setting.data.local

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