package ui.scores

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.global.cardPadding
import de.davidbattefeld.berlinskylarks.testdata.testGame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresDetailScreen(
    matchID: Int,
) {
    val vm: ScoresViewModel = viewModel(LocalContext.current as ComponentActivity)
    val game = vm.getFilteredGame(matchID) ?: testGame

    var showLocationData by rememberSaveable { mutableStateOf(false) }
    var showOfficialsData by rememberSaveable { mutableStateOf(false) }
    var showStatisticsData by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            columns = GridCells.Adaptive(minSize = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                FilterChip(
                    onClick = { showLocationData = !showLocationData },
                    label = {
                        Text("Location")
                    },
                    selected = showLocationData,
                    leadingIcon = if (showLocationData) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
            item {
                FilterChip(
                    onClick = { showStatisticsData = !showStatisticsData },
                    label = { Text("Statistics") },
                    selected = showStatisticsData,
                    leadingIcon = if (showStatisticsData) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
            item {
                FilterChip(
                    onClick = { showOfficialsData = !showOfficialsData },
                    label = { Text("Officials") },
                    selected = showOfficialsData,
                    leadingIcon = if (showOfficialsData) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }
        Divider(modifier = Modifier.padding(8.dp))
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(cardPadding),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    ScoresDetailMainInfo(game)
                }
            }
            item {
                ScoreDetailOfficialsSection(showOfficialsData, game)
            }
        }
    }
}