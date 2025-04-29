package edu.iesam.gametracker.app.extensions

import android.widget.ImageView
import coil.load
import edu.iesam.gametracker.R

fun ImageView.loadUrl(url: String) {
    this.load(url) {
        placeholder(R.drawable.ic_load)
    }
}
