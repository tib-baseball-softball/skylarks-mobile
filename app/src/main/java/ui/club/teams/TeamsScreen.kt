package ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.TeamsViewModel
import de.davidbattefeld.berlinskylarks.enums.ViewState
import ui.utility.ContentNotFoundView
import ui.utility.LoadingView

@Composable
fun TeamsScreen() {
    val vm: TeamsViewModel = viewModel(LocalContext.current as ComponentActivity)
    val listState = rememberLazyListState()

    Surface(
        shape = RoundedCornerShape(size = 12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState
        ) {
            when (vm.viewState) {
                ViewState.NoResults, ViewState.NotInitialised -> {
                    item {
                        ContentNotFoundView("teams")
                    }
                }
                ViewState.Loading -> {
                    item {
                        LoadingView()
                    }
                }
                ViewState.Found -> {
                    itemsIndexed(vm.teams) { index, team ->
                        Column {
                            ListItem(
                                headlineContent = { Text(team.name) },
                                supportingContent = { Text(team.bsmShortName) },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Filled.Groups,
                                        contentDescription = "team icon",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        imageVector = Icons.Filled.ChevronRight,
                                        contentDescription = "click to show more info",
                                    )
                                }
                            )
                            // last item does not have a divider
                            if (index + 1 != vm.teams.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (vm.teams.isEmpty()) {
            vm.load()
        }
    }
}