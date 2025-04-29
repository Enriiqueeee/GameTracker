package edu.iesam.gametracker.features.setting.presentation.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import edu.iesam.gametracker.R
import edu.iesam.gametracker.features.setting.domain.resources.Resources

class ResourcesAdapter : ListAdapter<Resources, ResourcesViewHolder>(ResourcesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_bottom_sheet_resources, parent, false)
        return ResourcesViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(viewHolder: ResourcesViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }
}