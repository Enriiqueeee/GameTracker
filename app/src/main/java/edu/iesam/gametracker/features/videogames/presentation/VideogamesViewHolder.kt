package edu.iesam.gametracker.features.videogames.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.ViewVideogamesItemBinding
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewVideogamesItemBinding

    fun bind(videogame: Videogame){
        binding = ViewVideogamesItemBinding.bind(view)
        binding.apply {
            image.loadUrl(videogame.backgroundImage)
            nameGame.text = videogame.name
            released.text = videogame.released
            rating.text = videogame.rating.toString()
        }
    }
}