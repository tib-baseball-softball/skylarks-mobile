package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.cardPadding
import de.davidbattefeld.berlinskylarks.ui.scores.box.ScoresDetailBoxScoreSection
import de.davidbattefeld.berlinskylarks.ui.scores.gamereport.ScoresDetailGameReportSection
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoreDetailViewModel

@Composable
fun ScoresDetailScreen(
    matchID: Int,
    vm: ScoreDetailViewModel
) {
    val gameDecorator by vm.game.collectAsState()
    val gameReport by vm.gameReport.collectAsState()
    val gameBoxScore by vm.boxScore.collectAsState()

    var showLocationData by rememberSaveable { mutableStateOf(false) }
    var showOfficialsData by rememberSaveable { mutableStateOf(false) }
    var showStatisticsData by rememberSaveable { mutableStateOf(false) }
    var showBoxScoreSection by rememberSaveable { mutableStateOf(false) }
    var showGameReportSection by rememberSaveable { mutableStateOf(false) }

    if (gameDecorator == null) {
        return ContentNotFoundView("games")
    }

    LazyColumn(
        modifier = Modifier,
    ) {
        item {
            Card(
                modifier = Modifier
                    .padding(cardPadding),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                gameDecorator?.let { ScoresDetailMainInfo(it) }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(100.dp),
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                    item {
                        FilterChip(
                            onClick = { showBoxScoreSection = !showBoxScoreSection },
                            label = { Text("Box Score") },
                            selected = showBoxScoreSection,
                            enabled = gameBoxScore != null,
                            leadingIcon = if (showBoxScoreSection) {
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
                            onClick = { showGameReportSection = !showGameReportSection },
                            label = { Text("Game Report") },
                            selected = showGameReportSection,
                            leadingIcon = if (showGameReportSection) {
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
            }
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(8.dp))
        }
        item {
            gameDecorator?.let {
                ScoreDetailStatisticsSection(showStatisticsData, it.game)
            }
        }
        item {
            gameDecorator?.let {
                ScoreDetailLocationSection(showLocationData, it.game)
            }
        }
        item {
            gameDecorator?.let {
                ScoreDetailOfficialsSection(showOfficialsData, it.game)
            }
        }
        item {
            ScoresDetailBoxScoreSection(
                show = showBoxScoreSection,
                boxScore = gameBoxScore,
            )
        }
        item {
            ScoresDetailGameReportSection(
                show = showGameReportSection,
                gameReport = gameReport,
            )
        }
    }
}