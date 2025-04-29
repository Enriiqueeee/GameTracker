package edu.iesam.gametracker.features.videogames.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.gametracker.R
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.ViewVideogamesItemBinding
import edu.iesam.gametracker.features.videogames.domain.GetVideogamesUseCase
import edu.iesam.gametracker.features.videogames.domain.Videogame

class VideogamesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewVideogamesItemBinding.bind(view)

    fun bind(
        videogameFeed: GetVideogamesUseCase.VideoGameFeed,
        onClick: ((Videogame) -> Unit)?,
        onDetailClick: ((Videogame) -> Unit)?,
        onShareClick: ((Videogame, Bitmap?) -> Unit)?
    ) {
        val videogame = videogameFeed.videogame

        binding.apply {
            image.loadUrl(videogame.backgroundImage)
            nameGame.text = videogame.name
            released.text = root.context.getString(
                R.string.released_date,
                videogame.released
            )
            rating.text = videogame.rating.toString()
            val genreNames = videogame.genres.joinToString(", ") { it.name }
            genres.text = view.context.getString(R.string.genres, genreNames)

            btnsave.setImageResource(
                if (videogameFeed.isFavorite) R.drawable.ic_favorite_click
                else R.drawable.ic_save
            )
            btnsave.setOnClickListener { onClick?.invoke(videogame) }
            root.setOnClickListener { onDetailClick?.invoke(videogame) }

            btnShare.setOnClickListener {
                val bitmap = (image.drawable as? BitmapDrawable)?.bitmap
                onShareClick?.invoke(videogame, bitmap)
            }
        }
    }
}

