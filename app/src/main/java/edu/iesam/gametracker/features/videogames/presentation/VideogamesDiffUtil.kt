package edu.iesam.gametracker.features.videogames.presentation

import androidx.recyclerview.widget.DiffUtil
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesDiffUtil : DiffUtil.ItemCallback<GetVideogamesUseCase.VideoGameFeed>() {
    override fun areItemsTheSame(
        oldItem: GetVideogamesUseCase.VideoGameFeed,
        newItem: GetVideogamesUseCase.VideoGameFeed
    ): Boolean {
        return oldItem.videogame.id == newItem.videogame.id
    }

    override fun areContentsTheSame(
        oldItem: GetVideogamesUseCase.VideoGameFeed,
        newItem: GetVideogamesUseCase.VideoGameFeed
    ): Boolean {
        return oldItem == newItem
    }

}