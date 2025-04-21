package edu.iesam.gametracker.features.setting.presentation.developer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import edu.iesam.gametracker.R
import edu.iesam.gametracker.features.setting.domain.developer.Developer

class DeveloperAdapter(
    private val onItemClick: (Developer) -> Unit
) : ListAdapter<Developer, DeveloperViewHolder>(DeveloperDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeveloperViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_bottom_sheet_developer, parent, false)
        return DeveloperViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(viewHolder: DeveloperViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }
}