package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.BSMTeam
import de.berlinskylarks.shared.utility.BSMUtility
import de.davidbattefeld.berlinskylarks.ui.nav.Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    bsmTeams: List<BSMTeam>,
    onTeamFilterChanged: (Int) -> Unit,
    selectedTeam: BSMTeam?,
) {
    var teamFilterExpanded by remember { mutableStateOf(false) }

    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = Home.title) },
        actions = {
            Box {
                OutlinedButton(
                    onClick = { teamFilterExpanded = !teamFilterExpanded }
                ) {
                    Text(
                        text = "${selectedTeam?.name ?: "None"} (${selectedTeam?.leagueEntries?.firstOrNull()?.league?.acronym ?: "None"})",
                    )
                    Icon(
                        modifier = Modifier.offset(x = 8.dp),
                        imageVector = Icons.Outlined.UnfoldMore,
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    expanded = teamFilterExpanded,
                    onDismissRequest = { teamFilterExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("None selected") },
                        onClick = {
                            onTeamFilterChanged(BSMUtility.NON_EXISTENT_ID)
                            teamFilterExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Groups,
                                contentDescription = null
                            )
                        }
                    )
                    bsmTeams.forEach {
                        DropdownMenuItem(
                            text = { Text("${it.name} (${it.leagueEntries?.firstOrNull()?.league?.acronym})") },
                            onClick = {
                                onTeamFilterChanged(it.id)
                                teamFilterExpanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Groups,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}