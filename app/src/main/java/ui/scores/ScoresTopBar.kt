package ui.scores

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.UnfoldMore
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.davidbattefeld.berlinskylarks.classes.data.DEFAULT_SETTINGS
import de.davidbattefeld.berlinskylarks.classes.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
fun ScoresTopBar(title: String, scrollBehavior: TopAppBarScrollBehavior) {
    val vm: ScoresViewModel = viewModel(LocalContext.current as ComponentActivity)
    var leagueFilterExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val readState = rememberPermissionState(Manifest.permission.READ_CALENDAR)
    val writeState = rememberPermissionState(Manifest.permission.WRITE_CALENDAR)

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
                            readState.status.isGranted && writeState.status.isGranted -> {
                                Button(onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }) {
                                    Text("Hide bottom sheet")
                                }
                            }

                            else -> {
                                LaunchedEffect(Unit) {
                                    readState.launchPermissionRequest()
                                }
                                Icon(
                                    Icons.Rounded.CalendarMonth,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Calendar permission not granted",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(Modifier.height(4.dp))
                                Text("This is required in order for the app to save games to calendars")

                                Button(
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(16.dp),
                                    onClick = {
                                        val intent =
                                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                                data = Uri.fromParts(
                                                    "package",
                                                    context.packageName,
                                                    null
                                                )
                                            }
                                        context.startActivity(intent)
                                    }) {
                                    Text("Go to settings")
                                }
                            }

                        }
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