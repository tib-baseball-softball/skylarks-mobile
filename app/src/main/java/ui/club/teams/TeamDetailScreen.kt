package ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
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
fun TeamDetailScreen(teamID: Int) {
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
                        ContentNotFoundView("players")
                    }
                }
                ViewState.Loading -> {
                    item {
                        LoadingView()
                    }
                }
                ViewState.Found -> {
                    itemsIndexed(vm.players) { index, player ->
                        Column {
                            PlayerRow(
                                player = player,
                                modifier = Modifier
                                    .clickable {
                                        // TODO: player detail page
                                    }
                            )
                            // last item does not have a divider
                            if (index + 1 != vm.players.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.loadPlayers(teamID)
    }
}