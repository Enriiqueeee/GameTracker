package edu.iesam.gametracker.app.extensions

import org.jsoup.Jsoup

fun String.cleanHtml(): String {
    return Jsoup.parse(this).text()
}