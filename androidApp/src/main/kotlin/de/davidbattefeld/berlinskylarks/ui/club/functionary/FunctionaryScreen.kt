package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.berlinskylarks.shared.data.model.Functionary
import de.berlinskylarks.shared.data.model.Person
import de.davidbattefeld.berlinskylarks.ui.viewmodels.AppViewModelProvider
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FunctionaryViewModel

@Composable
fun FunctionaryScreen(vm: FunctionaryViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val functionaries by vm.functionariesList.collectAsState(listOf())

    FunctionariesList(
        functionaries = functionaries.map {
            Functionary(
                id = it.id,
                category = it.category,
                function = it.function,
                mail = it.mail,
                person = Person(0, "M", "Ö", "P"),
                admission_date = it.admission_date
            )
        },
        onFunctionaryClick = { println("Clicked on functionary: $it") },
        modifier = Modifier,
    )
}