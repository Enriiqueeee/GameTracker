package edu.iesam.gametracker.app.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.annotation.Single

@Single
class AppIntent(private val content: Context) {

    fun openWebPage(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        content.startActivity(intent)
    }
}