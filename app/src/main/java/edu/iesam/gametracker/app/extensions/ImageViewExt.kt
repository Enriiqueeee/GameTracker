package edu.iesam.gametracker.app.extensions

import android.widget.ImageView
import coil.load

fun ImageView.loadUrl(url: String) {
    this.load(url)
}