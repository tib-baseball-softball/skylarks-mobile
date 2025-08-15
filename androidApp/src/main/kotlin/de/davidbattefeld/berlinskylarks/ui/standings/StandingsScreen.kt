package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.StandingsViewModel
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView

@Composable
fun StandingsScreen(
    vm: StandingsViewModel = viewModel(),
    detailRoute: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(clubCardPadding),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Insights,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Club Standings", style = MaterialTheme.typography.titleLarge)
                Text(text = "See records for all Club teams at a glance and get an overview.")
            }
        }
        Surface(
            shape = RoundedCornerShape(size = 12.dp),
            tonalElevation = 1.dp,
            modifier = Modifier
                .padding(8.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (vm.viewState) {
                    ViewState.NoResults, ViewState.NotInitialised -> {
                        item {
                            ContentNotFoundView("tables")
                        }
                    }

                    ViewState.Loading -> {
                        item {
                            LoadingView()
                        }
                    }

                    ViewState.Found -> {
                        itemsIndexed(vm.leagueGroups) { index, league ->
                            Column {
                                StandingsLeagueRow(
                                    leagueGroup = league,
                                    modifier = Modifier
                                        .clickable {
                                            detailRoute(league.id)
                                        }
                                )
                                // last item does not have a divider
                                if (index + 1 != vm.leagueGroups.size) {
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
    }
    LaunchedEffect(Unit) {
        if (vm.leagueGroups.isEmpty()) {
            vm.load()
        }
    }
}