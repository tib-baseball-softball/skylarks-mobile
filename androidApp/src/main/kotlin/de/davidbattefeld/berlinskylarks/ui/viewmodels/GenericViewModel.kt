package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.content.Context
import android.text.Html
import android.text.Spanned
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.model.JSONDataObject
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

abstract class GenericViewModel(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(), ViewModelInterface {
    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    var viewState by mutableStateOf(ViewState.NotInitialised)

    fun updateSelectedSeason(season: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedSeason(season)
        }
    }

    fun <T : JSONDataObject> getFiltered(id: Int, list: List<T>): T? {
        return list.firstOrNull { it.id == id }
    }

    fun readStaticMarkdownFile(fileName: String, context: Context): Spanned {
        val assetManager = context.assets
        val src = assetManager.open(fileName).bufferedReader().use { it.readText() }
        val flavour = CommonMarkFlavourDescriptor()
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
        val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()

        return Html.fromHtml(html, 0)
    }
}