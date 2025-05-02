package edu.iesam.gametracker.app.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import edu.iesam.gametracker.BuildConfig
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single
import java.io.File
import java.io.FileOutputStream

@Single
class ContentShare(private val context: Context) {

    fun shareContent(title: String, textToShare: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }
        context.startActivity(
            Intent.createChooser(sendIntent, title)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        )
    }

    fun shareContentWithImage(title: String, textToShare: String, imageUri: Uri) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, textToShare)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(
            Intent.createChooser(sendIntent, title)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        )
    }


    fun shareVideogame(videoGame: Videogame, bmp: Bitmap?) {
        val text = "${videoGame.name}\n${videoGame.released}"
        if (bmp == null) {
            shareContent(videoGame.name, text)
            return
        }

        val cacheDir = File(context.cacheDir, "shared_images")
            .apply { if (!exists()) mkdirs() }

        val imageFile = cacheDir.resolve("share_${videoGame.id}.png")
        FileOutputStream(imageFile).use { out ->
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        val authority = BuildConfig.APPLICATION_ID + ".fileprovider"
        Log.d("ContentShare", "Using FileProvider authority: $authority")

        val uri: Uri = FileProvider.getUriForFile(context, authority, imageFile)

        shareContentWithImage(videoGame.name, text, uri)
    }
}
