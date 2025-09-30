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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LeagueGroupsViewModel

@Composable
fun StandingsScreen(
    vm: LeagueGroupsViewModel,
    detailRoute: (Int) -> Unit,
) {
    val leagueGroups by vm.leagueGroups.collectAsState()

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
                        itemsIndexed(leagueGroups) { index, league ->
                            Column {
                                StandingsLeagueRow(
                                    leagueGroup = league,
                                    modifier = Modifier
                                        .clickable {
                                            detailRoute(league.id)
                                        }
                                )
                                // last item does not have a divider
                                if (index + 1 != leagueGroups.size) {
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
    }
    LaunchedEffect(Unit) {
        vm.viewState = ViewState.Found
    }
}