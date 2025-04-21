package edu.iesam.gametracker.features.setting.data.developer.remote

import edu.iesam.gametracker.features.setting.domain.developer.Developer


fun DeveloperFirebaseModel.toModel(): Developer {
    return Developer(
        this.id,
        this.name,
        this.githubUrl
    )
}