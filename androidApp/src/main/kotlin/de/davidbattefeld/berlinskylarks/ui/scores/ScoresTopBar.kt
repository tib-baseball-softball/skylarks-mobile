package de.davidbattefeld.berlinskylarks.ui.scores

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.davidbattefeld.berlinskylarks.LocalSnackbarHostState
import de.davidbattefeld.berlinskylarks.data.preferences.DEFAULT_SETTINGS
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import de.davidbattefeld.berlinskylarks.ui.calendar.PermissionNotGrantedView
import de.davidbattefeld.berlinskylarks.ui.utility.ConfirmationDialog
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
fun ScoresTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    vm: ScoresViewModel = viewModel()
) {
    var leagueFilterExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val readState = rememberPermissionState(Manifest.permission.READ_CALENDAR)
    val writeState = rememberPermissionState(Manifest.permission.WRITE_CALENDAR)
    var selectedCalID by remember { mutableStateOf<Long?>(null) }


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
            IconButton(onClick = {
                showBottomSheet = true
            }) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "add games to calendar"
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

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Column(
                        Modifier
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        when {
                            readState.status.isGranted -> {
                                LaunchedEffect(Unit) {
                                    vm.loadCalendars(context)
                                    writeState.launchPermissionRequest()
                                }

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    item {
                                        Text(
                                            text = "Select a Calendar to save games",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    items(vm.userCalendars) { userCalendar ->
                                        val isSelected = userCalendar.id == selectedCalID
                                        ListItem(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(shape = RoundedCornerShape(12.dp))
                                                .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                                                .clickable {
                                                    selectedCalID = userCalendar.id
                                                },
                                            leadingContent = {
                                                Icon(
                                                    imageVector = Icons.Filled.CalendarMonth,
                                                    contentDescription = "",
                                                )
                                            },
                                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                            headlineContent = {
                                                Text(text = userCalendar.displayName)
                                            },
                                            supportingContent = {
                                                Text(text = userCalendar.accountName)
                                            },
                                            trailingContent = {
                                                if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Check,
                                                        contentDescription = "item is selected",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                    )
                                                }
                                            }
                                        )
                                    }
                                    item {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.padding(vertical = 20.dp)
                                        ) {
                                            OutlinedButton(onClick = {
                                                scope.launch { sheetState.hide() }
                                                    .invokeOnCompletion {
                                                        if (!sheetState.isVisible) {
                                                            showBottomSheet = false
                                                            selectedCalID = null
                                                        }
                                                    }
                                            }) {
                                                Text("Cancel")
                                            }

                                            Button(
                                                onClick = {
                                                    scope.launch { sheetState.hide() }
                                                        .invokeOnCompletion {
                                                            if (!sheetState.isVisible) {
                                                                showBottomSheet = false
                                                            }
                                                        }
                                                    if (writeState.status.isGranted) {
                                                        showConfirmationDialog = true
                                                    } else {
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar("Calendar write access not granted.")
                                                        }
                                                    }
                                                },
                                                enabled = selectedCalID != null
                                            ) {
                                                Text("Add Games to Calendar")
                                            }
                                        }
                                    }
                                }
                            }

                            else -> {
                                LaunchedEffect(Unit) {
                                    readState.launchPermissionRequest()
                                }
                                PermissionNotGrantedView()
                            }

                        }
                    }
                }
            }

            if (showConfirmationDialog) {
                ConfirmationDialog(
                    icon = Icons.Filled.CalendarMonth,
                    onDismissRequest = { showConfirmationDialog = false },
                    onConfirmation = {
                        vm.addGamesToCalendar(
                            context = context, selectedCalID!!
                        )
                        showConfirmationDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Events have been added. They might take some time to show up in your calendar.")
                        }
                    },
                    dialogTitle = "Confirm adding events",
                    content = {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("The following events will be added:")
                            vm.games.forEach {
                                Text("${it.game.awayTeamName} @ ${it.game.homeTeamName} on ${it.localisedDate}")
                            }
                        }
                    }
                )
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