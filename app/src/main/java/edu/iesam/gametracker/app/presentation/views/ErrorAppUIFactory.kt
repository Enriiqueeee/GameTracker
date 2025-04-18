package edu.iesam.gametracker.app.presentation.views

import android.content.Context
import edu.iesam.gametracker.app.domain.ErrorApp
import org.koin.core.annotation.Factory

@Factory
class ErrorAppUIFactory(private val context: Context) {

    fun build(errorApp: ErrorApp): ErrorAppUI {
        return when (errorApp) {
            ErrorApp.DataErrorApp -> ServerErrorAppUI(context)
            ErrorApp.DataExpiredError -> ServerErrorAppUI(context)
            ErrorApp.InternetErrorApp -> ConnectionErrorAppUI(context)
            ErrorApp.ServerErrorApp -> ServerErrorAppUI(context)
            ErrorApp.UnknowErrorApp -> UnknownErrorAppUI(context)
        }
    }

}