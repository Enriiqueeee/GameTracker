package edu.iesam.gametracker.features.videogames.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import edu.iesam.gametracker.R
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesAdapter : ListAdapter<Videogame, VideogamesViewHolder>(VideogamesDiffUtil()) {

    private var onItemClick: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_videogames_item, parent, false)
        return VideogamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideogamesViewHolder, position: Int) {
        val videogame = currentList[position]
        holder.bind(videogame)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(videogame.id)
        }
    }
}
