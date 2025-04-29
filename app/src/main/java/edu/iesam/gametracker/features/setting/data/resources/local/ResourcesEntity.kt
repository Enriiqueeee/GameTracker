package edu.iesam.gametracker.features.setting.data.resources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val RESOURCE_TABLE = "resources"
const val RESOURCE_ID = "id"

@Entity(tableName = RESOURCE_TABLE)
class ResourcesEntity (
    @PrimaryKey @ColumnInfo(name = RESOURCE_ID) val id: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url_web") val urlWeb: String
)