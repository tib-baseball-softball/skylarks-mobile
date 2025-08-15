package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import android.icu.util.Calendar
import android.text.Html
import android.text.Spanned
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

class SettingsViewModel(application: Application) : GenericViewModel(application) {
    // 2021 is the first year with the new team name
    val possibleSeasons = (2021..Calendar.getInstance().get(Calendar.YEAR)).toList()

    fun readStaticMarkdownFile(fileName: String): Spanned {
        val assetManager = getApplication<Application>().applicationContext.assets
        val src = assetManager.open(fileName).bufferedReader().use { it.readText() }
        val flavour = CommonMarkFlavourDescriptor()
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
        val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()

        return Html.fromHtml(html, 0)
    }
}