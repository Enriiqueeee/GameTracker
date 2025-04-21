package edu.iesam.gametracker.features.setting.presentation.developer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.databinding.ViewItemBottomSheetDeveloperBinding
import edu.iesam.gametracker.features.setting.domain.developer.Developer

class DeveloperViewHolder(
    private val view: View,
    private val onItemClick: (Developer) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = ViewItemBottomSheetDeveloperBinding.bind(view)

    fun bind(developer: Developer) {
        binding.apply {
            binding.imageDeveloper.loadUrl(developer.avatar)
            binding.nameDeveloper.text = developer.name
            view.setOnClickListener { onItemClick(developer) }
        }
    }
}