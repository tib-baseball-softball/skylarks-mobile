package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.MenuDefaults
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
                            text = { Text("None selected") },
                            onCheckedChange = {
                                onTeamFilterChanged(BSMUtility.NON_EXISTENT_ID)
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
                        val groupItemCount = bsmTeams.size
                        bsmTeams.forEachIndexed { itemIndex, team ->
                            DropdownMenuItem(
                                text = { Text("${team.name} (${team.leagueEntries?.firstOrNull()?.league?.acronym})") },
                                onCheckedChange = {
                                    onTeamFilterChanged(team.id)
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
    )
}