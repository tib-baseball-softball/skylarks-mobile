package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.davidbattefeld.berlinskylarks.data.BerlinSkylarksApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FunctionaryViewModel(
                application = berlinSkylarksApplication(),
                functionaryRepository = berlinSkylarksApplication().container.functionaryRepository
            )
        }
    }
}

/**
 * Extension function to queries for [android.app.Application] object and returns an instance of
 * [BerlinSkylarksApplication].
 */
fun CreationExtras.berlinSkylarksApplication(): BerlinSkylarksApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BerlinSkylarksApplication)