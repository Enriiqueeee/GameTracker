package edu.iesam.gametracker.app.presentation.views

import android.content.Context
import edu.iesam.gametracker.R

interface ErrorAppUI {
    fun getImageError(): Int
    fun getTitleError(): String
    fun getDescriptionError(): String
}

class ServerErrorAppUI(val context: Context) : ErrorAppUI {
    override fun getImageError(): Int {
        return R.drawable.img_error_server
    }

    override fun getTitleError(): String {
        return context.getString(R.string.title_error_server)
    }

    override fun getDescriptionError(): String {
        return context.getString(R.string.description_error_server)
    }
}

class ConnectionErrorAppUI(val context: Context) : ErrorAppUI {
    override fun getImageError(): Int {
        return R.drawable.img_error_connection
    }

    override fun getTitleError(): String {
        return context.getString(R.string.title_error_connection)
    }

    override fun getDescriptionError(): String {
        return context.getString(R.string.description_error_connection)
    }
}

class UnknownErrorAppUI(val context: Context) : ErrorAppUI {
    override fun getImageError(): Int {
        return R.drawable.img_error_unknown
    }

    override fun getTitleError(): String {
        return context.getString(R.string.title_error_unknown)
    }

    override fun getDescriptionError(): String {
        return context.getString(R.string.description_error_unknown)
    }
}