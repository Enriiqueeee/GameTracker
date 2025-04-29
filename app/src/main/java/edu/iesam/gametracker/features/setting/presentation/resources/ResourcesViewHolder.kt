package edu.iesam.gametracker.features.setting.presentation.resources

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import edu.iesam.gametracker.app.extensions.loadUrl
import edu.iesam.gametracker.app.presentation.AppIntent
import edu.iesam.gametracker.databinding.ViewItemBottomSheetResourcesBinding
import edu.iesam.gametracker.features.setting.domain.resources.Resources


class ResourcesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewItemBottomSheetResourcesBinding.bind(view)

    fun bind(resource: Resources) {
        binding.apply {
            val nav = AppIntent(view.context)
            iconResource.loadUrl(resource.image)
            name.text = resource.name
            item.setOnClickListener {
                nav.openWebPage(resource.urlWeb)
            }
        }
    }
}