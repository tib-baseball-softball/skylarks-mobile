package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.berlinskylarks.shared.data.model.SkylarksTeam
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.AppViewModelProvider
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TeamsViewModel

@Composable
fun TeamsScreen(
    teamsDetailRoute: (Int) -> Unit,
    vm: TeamsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val listState = rememberLazyListState()

    Surface(
        shape = RoundedCornerShape(size = 12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState
        ) {
            when (vm.viewState) {
                ViewState.NoResults, ViewState.NotInitialised -> {
                    item {
                        ContentNotFoundView("teams")
                    }
                }

                ViewState.Loading -> {
                    item {
                        LoadingView()
                    }
                }

                ViewState.Found -> {
                    itemsIndexed<SkylarksTeam>(vm.teams) { index, team ->
                        Column {
                            TeamsRow(
                                team = team,
                                modifier = Modifier
                                    .clickable {
                                        teamsDetailRoute(team.id)
                                    }
                            )
                            // last item does not have a divider
                            if (index + 1 != vm.teams.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }

                ViewState.Error -> {
                    item {
                        Text("An error occured loading data.")
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (vm.teams.isEmpty()) {
            vm.load()
        }
    }
}