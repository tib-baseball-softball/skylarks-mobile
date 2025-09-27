package de.davidbattefeld.berlinskylarks.ui.scores

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.nav.ScoresDetail
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel.TabState

@Composable
fun ScoresScreen(
    modifier: Modifier = Modifier,
    detailRoute: (ScoresDetail) -> Unit,
    vm: ScoresViewModel,
) {
    val games by vm.games.collectAsState()
    var showExternalGames by rememberSaveable { mutableStateOf(true) }
    val tabTitles = listOf("Previous", "Current", "Next", "Any")

    LazyVerticalGrid(
        modifier = modifier.padding(horizontal = 6.dp),
        columns = GridCells.Adaptive(minSize = 350.dp)
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(horizontal = 10.dp)) {
                tabTitles.forEachIndexed { index, title ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = tabTitles.size
                        ),
                        selected = vm.tabState.ordinal == index,
                        onClick = {
                            vm.tabState = TabState.entries.toTypedArray()
                                .getOrElse(index) { TabState.CURRENT }
                            vm.load()
                        },
                        label = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            Row(
                modifier = Modifier
                    .padding(14.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8F)
                ) {
                    Text(
                        text = "Show External Games",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Controls whether the list is filtered by just Skylarks games.",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                Spacer(modifier = Modifier.weight(1.0F))
                Switch(
                    modifier = Modifier.semantics {
                        contentDescription = "Show external Games"
                    },
                    checked = showExternalGames,
                    onCheckedChange = { showExternalGames = it },
                    colors = SwitchDefaults.colors(

                    )
                )
            }
        }
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        }
        if (games.isEmpty()) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                ContentNotFoundView("games")
            }
        } else {
            items(games) { gameDecorator ->
                ScoresItem(
                    gameDecorator = gameDecorator,
                    modifier = Modifier
                        .clickable {
                            detailRoute(
                                ScoresDetail(
                                    id = gameDecorator.game.id,
                                    matchID = gameDecorator.game.matchID
                                )
                            )
                        }
                )
            }
        }

//        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
//            LoadingView()
//        }

//        item {
//            Text("An error occured loading data.")
//        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 400,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 400
)
@Composable
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        // ScoresScreen()
    }
}