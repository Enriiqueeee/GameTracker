package edu.iesam.gametracker.features.setting.data.resources.remote

import com.google.firebase.firestore.PropertyName

class ResourcesFirebaseModel (
    var id: Int = 0,
    var image : String = "",
    var name: String = "",
    @get:PropertyName("url_web")
    @set:PropertyName("url_web") var urlWeb: String = "",
)