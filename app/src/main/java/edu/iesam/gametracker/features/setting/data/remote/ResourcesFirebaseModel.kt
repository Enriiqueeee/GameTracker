package edu.iesam.gametracker.features.setting.data.remote

import com.google.firebase.firestore.PropertyName

class ResourcesFirebaseModel (
    var id: Int = 0,
    var title: String = "",
    @get:PropertyName("url_resource")
    @set:PropertyName("url_resource") var urlResource: String = ""
)