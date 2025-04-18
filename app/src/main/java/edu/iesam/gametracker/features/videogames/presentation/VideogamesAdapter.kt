package edu.iesam.gametracker.features.videogames.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import edu.iesam.gametracker.R
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesAdapter() :
    ListAdapter<GetVideogamesUseCase.VideoGameFeed, VideogamesViewHolder>(VideogamesDiffUtil()) {

    private var onItemClick: ((Videogame) -> Unit)? = null
    private var onDetailClick: ((Videogame) -> Unit)? = null
    private var onShareClick: ((Videogame) -> Unit)? = null

    fun setOnItemClickListener(listener: (Videogame) -> Unit) {
        onItemClick = listener
    }

    fun setOnDetailClickListener(listener: (Videogame) -> Unit) {
        onDetailClick = listener
    }

    fun setOnShareClickListener(listener: (Videogame) -> Unit) {
        onShareClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogamesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_videogames_item, parent, false)
        return VideogamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideogamesViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick, onDetailClick, onShareClick)
    }
}
