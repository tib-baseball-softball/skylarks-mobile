package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.AppViewModelProvider
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FunctionaryViewModel

@Composable
fun FunctionaryScreen(vm: FunctionaryViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val functionaries by vm.functionariesList.collectAsState(listOf())

    Column {
        functionaries.forEach { functionary ->
            Text(functionary.function)
        }
    }
}