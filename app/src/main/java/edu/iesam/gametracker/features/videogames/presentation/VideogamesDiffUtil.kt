package edu.iesam.gametracker.features.videogames.presentation

import androidx.recyclerview.widget.DiffUtil
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesDiffUtil : DiffUtil.ItemCallback<Videogame>() {
    override fun areItemsTheSame(oldItem: Videogame, newItem: Videogame): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Videogame, newItem: Videogame): Boolean {
        return oldItem == newItem
    }

}