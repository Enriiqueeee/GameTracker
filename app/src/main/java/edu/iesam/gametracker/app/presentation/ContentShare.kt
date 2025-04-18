package edu.iesam.gametracker.app.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.annotation.Single

@Single
class ContentShare(private val context: Context) {

    fun shareContent(title: String, textToShare: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(shareIntent)
    }

    fun shareContentWithImage(title: String, textToShare: String, imageUri: Uri) {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, textToShare)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }.let { intent ->
            context.startActivity(
                Intent.createChooser(intent, title)
                    .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
            )
        }
    }
}