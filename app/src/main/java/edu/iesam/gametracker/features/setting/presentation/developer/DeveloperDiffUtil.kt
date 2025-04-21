package edu.iesam.gametracker.features.setting.presentation.developer

import androidx.recyclerview.widget.DiffUtil
import edu.iesam.gametracker.features.setting.domain.developer.Developer

class DeveloperDiffUtil : DiffUtil.ItemCallback<Developer>(){
    override fun areItemsTheSame(
        oldItem: Developer,
        newItem: Developer
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Developer,
        newItem: Developer
    ): Boolean {
        return oldItem == newItem
    }
}