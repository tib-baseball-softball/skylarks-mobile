package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FunctionaryViewModel

@Composable
fun FunctionaryScreen(vm: FunctionaryViewModel = viewModel()) {
    val functionaries by vm.functionariesList.collectAsState()

    FunctionariesList(
        functionaries = functionaries,
        onFunctionaryClick = { println("Clicked on functionary: $it") },
        modifier = Modifier,
    )
}