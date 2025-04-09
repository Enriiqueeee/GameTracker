package edu.iesam.gametracker.features.videogames.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val FAVORITE_TABLE = "favorite"
const val FAVORITE_ID = "id"

@Entity(tableName = FAVORITE_TABLE)
class FavoriteEntity (
    @PrimaryKey @ColumnInfo(name = FAVORITE_ID) val id: Int,
    @ColumnInfo(name = "videogameFavorite") val favorite: Boolean = false
)