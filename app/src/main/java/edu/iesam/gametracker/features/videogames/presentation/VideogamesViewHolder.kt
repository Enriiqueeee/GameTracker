package edu.iesam.gametracker.features.videogames.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.ViewVideogamesItemBinding
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ViewVideogamesItemBinding = ViewVideogamesItemBinding.bind(view)

    fun bind(videogameFeed: GetVideogamesUseCase.VideoGameFeed, onClick: ((Videogame) -> Unit)?) {


        binding.apply {
            image.loadUrl(videogameFeed.videogame.backgroundImage)
            nameGame.text = videogameFeed.videogame.name
            released.text = videogameFeed.videogame.released
            rating.text = videogameFeed.videogame.rating.toString()

            btnsave.setImageResource(
                if (videogameFeed.isFavorite) R.drawable.ic_favorite_click
                else R.drawable.ic_save
            )

            onClick?.let {
                btnsave.setOnClickListener {
                    onClick.invoke(videogameFeed.videogame)
                }
            }
        }
    }
}
