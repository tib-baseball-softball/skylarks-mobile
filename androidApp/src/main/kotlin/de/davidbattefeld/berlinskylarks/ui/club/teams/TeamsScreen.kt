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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TeamsViewModel

@Composable
fun TeamsScreen(
    teamsDetailRoute: (Int) -> Unit,
    vm: TeamsViewModel,
) {
    val listState = rememberLazyListState()
    val teams by vm.teams.collectAsState()

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
                    itemsIndexed(teams) { index, team ->
                        Column {
                            TeamsRow(
                                team = team,
                                modifier = Modifier
                                    .clickable {
                                        teamsDetailRoute(team.id)
                                    }
                            )
                            // last item does not have a divider
                            if (index + 1 != teams.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }

                ViewState.Error -> {
                    item {
                        Text("An error occurred loading data.")
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        vm.viewState = ViewState.Found
    }
}