package ui.scores

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.data.DEFAULT_SETTINGS
import de.davidbattefeld.berlinskylarks.classes.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScoresTopBar(title: String, scrollBehavior: TopAppBarScrollBehavior) {
    val vm: ScoresViewModel = viewModel(LocalContext.current as ComponentActivity)
    var leagueFilterExpanded by remember { mutableStateOf(false) }

    val userPreferences by vm.userPreferencesFlow.collectAsState(initial = DEFAULT_SETTINGS)

    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Button"
                )
            }
            Spacer(modifier = Modifier.weight(1.0F))

            Box {
                OutlinedButton(
                    onClick = { leagueFilterExpanded = !leagueFilterExpanded }
                ) {
                    Text(
                        text = vm.filteredLeagueGroup.name
                    )
                    Icon(
                        modifier = Modifier.offset(x = 8.dp),
                        imageVector = Icons.Outlined.UnfoldMore,
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    expanded = leagueFilterExpanded,
                    onDismissRequest = { leagueFilterExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Leagues") },
                        onClick = {
                           vm.onLeagueFilterChanged(testLeagueGroup)
                           leagueFilterExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Groups,
                                contentDescription = null
                            )
                        }
                    )
                    vm.leagueGroups.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                vm.onLeagueFilterChanged(it)
                                leagueFilterExpanded = false
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
    LaunchedEffect(Unit) {
        // this is kinda hacky, but after 2h still no better way to invalidate filter on season change
        if (vm.filteredLeagueGroup.season != userPreferences.season) {
            vm.filteredLeagueGroup = testLeagueGroup
        }
    }
}