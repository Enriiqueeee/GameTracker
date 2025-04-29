package edu.iesam.gametracker.features.setting.presentation.resources

import androidx.recyclerview.widget.DiffUtil
import edu.iesam.gametracker.features.setting.domain.resources.Resources

class ResourcesDiffUtil : DiffUtil.ItemCallback<Resources>() {
    override fun areItemsTheSame(
        oldItem: Resources,
        newItem: Resources
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Resources,
        newItem: Resources
    ): Boolean {
        return oldItem == newItem
    }

}