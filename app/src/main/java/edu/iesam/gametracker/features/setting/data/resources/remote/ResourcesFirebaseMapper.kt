package edu.iesam.gametracker.features.setting.data.resources.remote

import edu.iesam.gametracker.features.setting.domain.resources.Resources

fun ResourcesFirebaseModel.toModel(): Resources{
    return Resources(
        this.id,
        this.image,
        this.name,
        this.urlWeb
    )
}