package edu.iesam.gametracker.features.setting.data.developer.remote

import com.google.firebase.firestore.PropertyName

class DeveloperFirebaseModel(
    var id: Int = 0,
    var name: String = "",
    @get:PropertyName("github_url")
    @set:PropertyName("github_url") var githubUrl: String = "",
)