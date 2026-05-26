package de.davidbattefeld.berlinskylarks.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility
import de.davidbattefeld.berlinskylarks.ui.nav.GameReportDetail
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.News
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import kotlin.time.Instant

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    vm: NewsViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
    detailRoute: (GameReportDetail) -> Unit,
) {
    val seasonOptions = vm.possibleSeasons
    val gameReports by vm.gameReports.collectAsStateWithLifecycle()
    val teams by vm.tibTeams.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val selectedTeam by vm.selectedTeam.collectAsStateWithLifecycle()
    val selectedSeason by vm.selectedSeason.collectAsStateWithLifecycle()

    var teamFilterExpanded by remember { mutableStateOf(false) }
    var seasonFilterExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = News.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = {
            SkylarksBottomBar(
                topLevelBackStack,
                navigationType
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { vm.refresh() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Sync Game Reports"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    OutlinedButton(
                        onClick = { seasonFilterExpanded = !seasonFilterExpanded }
                    ) {
                        if (selectedSeason == null) {
                            Text(text = "All Seasons")
                        } else {
                            Text(text = selectedSeason.toString())
                        }
                        Icon(
                            modifier = Modifier.offset(x = 8.dp),
                            imageVector = Icons.Outlined.UnfoldMore,
                            contentDescription = ""
                        )
                    }

                    val groupInteractionSource = remember { MutableInteractionSource() }
                    DropdownMenuPopup(
                        expanded = seasonFilterExpanded,
                        onDismissRequest = { seasonFilterExpanded = false }
                    ) {
                        DropdownMenuGroup(
                            shapes = MenuDefaults.groupShape(0, 2),
                            interactionSource = groupInteractionSource,
                        ) {
                            DropdownMenuItem(
                                text = { Text("All Seasons") },
                                onCheckedChange = {
                                    vm.onSeasonChanged(null)
                                    seasonFilterExpanded = false
                                },
                                shapes = MenuDefaults.itemShape(0, 1),
                                leadingIcon = {
                                    if (selectedSeason == null) {
                                        Icon(
                                            Icons.Outlined.Check,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            Icons.Outlined.CalendarMonth,
                                            contentDescription = null
                                        )
                                    }
                                },
                                checked = selectedSeason == null,
                                colors = MenuDefaults.selectableItemColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(MenuDefaults.HorizontalDividerPadding)
                        )
                        DropdownMenuGroup(
                            shapes = MenuDefaults.groupShape(1, 2),
                            interactionSource = groupInteractionSource,
                        ) {
                            val groupItemCount = seasonOptions.size
                            seasonOptions.forEachIndexed { itemIndex, season ->
                                DropdownMenuItem(
                                    text = { Text(season.toString()) },
                                    onCheckedChange = {
                                        vm.onSeasonChanged(season)
                                        seasonFilterExpanded = false
                                    },
                                    shapes = MenuDefaults.itemShape(itemIndex, groupItemCount),
                                    leadingIcon = {
                                        if (selectedSeason == season) {
                                            Icon(
                                                Icons.Outlined.Check,
                                                contentDescription = null
                                            )
                                        } else {
                                            Icon(
                                                Icons.Outlined.CalendarMonth,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    checked = selectedSeason == season,
                                    colors = MenuDefaults.selectableItemColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                )
                            }
                        }
                    }
                }
                Box {
                    OutlinedButton(
                        onClick = { teamFilterExpanded = !teamFilterExpanded }
                    ) {
                        if (selectedTeam == null) {
                            Text(text = "All Teams")
                        } else {
                            Text(
                                text = "${selectedTeam!!.name} (${selectedTeam!!.bsmShortName})",
                            )
                        }
                        Icon(
                            modifier = Modifier.offset(x = 8.dp),
                            imageVector = Icons.Outlined.UnfoldMore,
                            contentDescription = ""
                        )
                    }
                    val teamGroupInteractionSource = remember { MutableInteractionSource() }
                    DropdownMenuPopup(
                        expanded = teamFilterExpanded,
                        onDismissRequest = { teamFilterExpanded = false }
                    ) {
                        DropdownMenuGroup(
                            shapes = MenuDefaults.groupShape(0, 2),
                            interactionSource = teamGroupInteractionSource,
                        ) {
                            DropdownMenuItem(
                                text = { Text("All Teams") },
                                onCheckedChange = {
                                    vm.onTeamFilterChanged(null)
                                    teamFilterExpanded = false
                                },
                                shapes = MenuDefaults.itemShape(0, 1),
                                leadingIcon = {
                                    if (selectedTeam == null) {
                                        Icon(
                                            Icons.Outlined.Check,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            Icons.Outlined.Groups,
                                            contentDescription = null
                                        )
                                    }
                                },
                                checked = selectedTeam == null,
                                colors = MenuDefaults.selectableItemColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(MenuDefaults.HorizontalDividerPadding)
                        )
                        DropdownMenuGroup(
                            shapes = MenuDefaults.groupShape(1, 2),
                            interactionSource = teamGroupInteractionSource,
                        ) {
                            val groupItemCount = teams.size
                            teams.forEachIndexed { itemIndex, team ->
                                DropdownMenuItem(
                                    text = { Text("${team.name} (${team.bsmShortName})") },
                                    onCheckedChange = {
                                        vm.onTeamFilterChanged(team.id)
                                        teamFilterExpanded = false
                                    },
                                    shapes = MenuDefaults.itemShape(itemIndex, groupItemCount),
                                    leadingIcon = {
                                        if (selectedTeam?.id == team.id) {
                                            Icon(
                                                Icons.Outlined.Check,
                                                contentDescription = null
                                            )
                                        } else {
                                            Icon(
                                                Icons.Outlined.Groups,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    checked = selectedTeam?.id == team.id,
                                    colors = MenuDefaults.selectableItemColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                )
                            }
                        }
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 350.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (gameReports.isEmpty()) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        ContentNotFoundView("game reports")
                    }
                } else {
                    items(gameReports) {
                        NewsItemCard(
                            title = it.title,
                            date = AndroidDateTimeUtility.formatDate(Instant.parse(it.date)),
                            teaser = it.teaserText,
                            image = it.gallery?.firstOrNull(),
                            modifier = Modifier
                                .clickable {
                                    detailRoute(
                                        GameReportDetail(it.gameId)
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}
