package edu.iesam.gametracker.features.setting.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val RESOURCE_TABLE = "setting"
const val RESOURCE_ID = "id"

@Entity(tableName = RESOURCE_TABLE)
class ResourceEntity (

    @PrimaryKey @ColumnInfo(name = RESOURCE_ID) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url_resource") val urlResource: String
)