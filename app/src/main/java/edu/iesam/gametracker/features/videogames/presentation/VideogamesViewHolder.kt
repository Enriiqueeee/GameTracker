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

    fun bind(
        videogameFeed: GetVideogamesUseCase.VideoGameFeed,
        onClick: ((Videogame) -> Unit)?,
        onDetailClick: ((Videogame) -> Unit)?,
        onShareClick: ((Videogame) -> Unit)?
    ) {


        binding.apply {
            image.loadUrl(videogameFeed.videogame.backgroundImage)
            nameGame.text = videogameFeed.videogame.name
            released.text = root.context.getString(
                R.string.released_date,
                videogameFeed.videogame.released
            )
            rating.text = videogameFeed.videogame.rating.toString()
            val genreNames = videogameFeed.videogame.genres
                .joinToString(", ") { it.name }

            genres.text = itemView.context.getString(R.string.genres, genreNames)


            btnsave.setImageResource(
                if (videogameFeed.isFavorite) R.drawable.ic_favorite_click
                else R.drawable.ic_save
            )

            onClick?.let {
                btnsave.setOnClickListener {
                    onClick.invoke(videogameFeed.videogame)
                }
            }

            onDetailClick?.let {
                root.setOnClickListener {
                    onDetailClick.invoke(videogameFeed.videogame)
                }
            }

            btnShare.setOnClickListener {
                onShareClick?.invoke(videogameFeed.videogame)
            }

        }
    }
}
