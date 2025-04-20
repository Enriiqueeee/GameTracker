package edu.iesam.gametracker.features.setting.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val DEVELOPER_TABLE = "developer"
const val DEVELOPER_ID = "id"

@Entity(tableName = DEVELOPER_TABLE)
class DeveloperEntity (
    @PrimaryKey @ColumnInfo(name = DEVELOPER_ID) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "avatar") val avatar: String
)