package edu.iesam.gametracker.features.videogames.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.iesam.gametracker.features.videogames.domain.Genre


const val VIDEOGAME_TABLE = "videogame"
const val VIDEOGAME_ID = "id"

@Entity(tableName = VIDEOGAME_TABLE)
class VideogamesEntity(
    @PrimaryKey @ColumnInfo(name = VIDEOGAME_ID) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "released") val released: String,
    @ColumnInfo(name = "background_image") val backgroundImage: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "playtime") val playtime: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "order_index") val orderIndex: Int
)

