package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.content.Context
import android.icu.util.Calendar
import android.text.Html
import android.text.Spanned
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

class SettingsViewModel(userPreferencesRepository: UserPreferencesRepository) :
    GenericViewModel(userPreferencesRepository) {
    // 2021 is the first year with the new team name
    val possibleSeasons = (2021..Calendar.getInstance().get(Calendar.YEAR)).toList()

    fun readStaticMarkdownFile(fileName: String, context: Context): Spanned {
        val assetManager = context.assets
        val src = assetManager.open(fileName).bufferedReader().use { it.readText() }
        val flavour = CommonMarkFlavourDescriptor()
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
        val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()

        return Html.fromHtml(html, 0)
    }
}