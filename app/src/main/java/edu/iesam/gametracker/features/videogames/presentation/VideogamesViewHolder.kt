package edu.iesam.gametracker.features.videogames.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.ViewVideogamesItemBinding
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ViewVideogamesItemBinding = ViewVideogamesItemBinding.bind(view)

    private var isFavorited = false

    fun bind(videogame: Videogame, isFavorite: Boolean, onFavoriteToggle: (Videogame, Boolean) -> Unit) {
        isFavorited = isFavorite

        binding.apply {
            image.loadUrl(videogame.backgroundImage)
            nameGame.text = videogame.name
            released.text = videogame.released
            rating.text = videogame.rating.toString()

            btnsave.setImageResource(
                if (isFavorited) R.drawable.ic_favorite_click
                else R.drawable.ic_save
            )

            btnsave.setOnClickListener {
                isFavorited = !isFavorited
                btnsave.setImageResource(
                    if (isFavorited) R.drawable.ic_favorite_click
                    else R.drawable.ic_save
                )
                onFavoriteToggle(videogame, isFavorited)
            }
        }
    }
}
