package edu.iesam.gametracker.app.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import edu.iesam.gametracker.app.presentation.hide
import edu.iesam.gametracker.app.presentation.visible
import edu.iesam.gametracker.databinding.ViewErrorBinding

class ErrorAppView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        hide()
    }

    fun render(errorAppUI: ErrorAppUI) {
        binding.imgError.setImageResource(errorAppUI.getImageError())
        binding.titleError.text = errorAppUI.getTitleError()
        binding.descriptionError.text = errorAppUI.getDescriptionError()
        visible()
    }
}