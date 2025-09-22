package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import de.davidbattefeld.berlinskylarks.data.BerlinSkylarksApplication

//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        initializer {
//            FunctionaryViewModel(
//                functionaryRepository = berlinSkylarksApplication().container.functionaryRepository,
//                userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
//            )
//        }
//        initializer {
//            SettingsViewModel(
//                userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
//            )
//        }
//        initializer {
//            ScoresViewModel(
//                userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
//            )
//        }
//        initializer {
//            LeagueGroupsViewModel(
//                userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
//            )
//        }
//        initializer {
//            TeamsViewModel(
//                userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
//            )
//        }
//
//    }
//}

/**
 * Extension function to queries for [android.app.Application] object and returns an instance of
 * [BerlinSkylarksApplication].
 */
fun CreationExtras.berlinSkylarksApplication(): BerlinSkylarksApplication {
    Log.d("ViewModelFactory", "Available keys in CreationExtras: $this") // Add logging
    val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]

    if (app == null) {
        Log.e("ViewModelFactory", "APPLICATION_KEY is null!")
    }
    return (app as BerlinSkylarksApplication)
}