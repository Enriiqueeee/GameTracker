package edu.iesam.gametracker.features.setting.data.remote

import edu.iesam.gametracker.features.setting.domain.Resource

fun ResourcesFirebaseModel.toModel(): Resource {
    return Resource(
        this.id,
        this.title,
        this.urlResource
    )
}