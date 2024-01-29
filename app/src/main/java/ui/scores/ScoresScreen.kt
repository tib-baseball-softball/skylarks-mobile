package ui.scores

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.global.cardPadding
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun ScoresScreen(
    modifier: Modifier = Modifier,
    detailRoute: (Int) -> Unit,
) {
    val scoresViewModel: ScoresViewModel = viewModel(LocalContext.current as ComponentActivity)
    var showExternalGames by remember { mutableStateOf(true) }
    val tabTitles = listOf("Previous", "Current", "Next", "Any")

    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        item {
            TabRow(selectedTabIndex = scoresViewModel.tabState) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = scoresViewModel.tabState == index,
                        onClick = {
                            scoresViewModel.tabState = index
                            scoresViewModel.load()
                        },
                        text = {
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
        item {
            Card(
                modifier = Modifier
                    .padding(cardPadding),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp),
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
        }
        item {
            Divider(modifier = Modifier.padding(horizontal = 12.dp))
        }
        item {
            if (scoresViewModel.games.isEmpty()) {
                Card(
                    modifier = Modifier
                        .padding(cardPadding)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(
                            text = "There are no games to display.",
                            modifier = Modifier
                                .weight(1.0F)
                        )
                    }
                }
            }
        }
        items(scoresViewModel.games) { game ->
            ScoresItem(
                game = game,
                modifier = Modifier
                    .clickable {
                        detailRoute(game.id)
                    }
            )
        }
        item {
            Row {
                Button(onClick = {
                    scoresViewModel.load()
                }) {
                    Text(text = "Load games")
                }
            }
        }
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