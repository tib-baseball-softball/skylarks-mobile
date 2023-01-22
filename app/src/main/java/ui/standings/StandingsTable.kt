package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.LeagueTable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandingsTable(leagueTable: LeagueTable) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val headerStyle = MaterialTheme.typography.titleMedium

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = "Standings") },
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(it) , // no clue how this works, but breaks without it #Classic
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surfaceVariant)
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "#",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.1F)
                                .padding(8.dp)
                        )
                        Text(
                            "Team",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.55F, fill = true)
                                .padding(8.dp)
                        )
                        Text(
                            "W",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.1F)
                                .padding(8.dp)
                        )
                        Text(
                            "L",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.1F)
                                .padding(8.dp)
                        )
                        Text(
                            "%",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.1F)
                                .padding(8.dp)
                        )
                        Text(
                            "GB",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.12F)
                                .padding(6.dp)
                        )
                        Text(
                            "Srk",
                            style = headerStyle,
                            modifier = Modifier
                                .weight(0.15F)
                                .padding(8.dp)
                        )
                    }
                }
                items(leagueTable.rows) { row ->
                    StandingsTableRow(row = row)
                    Divider(modifier = Modifier.padding(horizontal = 12.dp))
                }
                item {
                    Card(
                        modifier = Modifier.padding(top = 20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(text = "Legend:", style = MaterialTheme.typography.labelLarge)
                            Text(
                                text = """
                                        W: Wins
                                        L: Losses
                                        %: Winning Percentage
                                        GB: Games Behind (first place, assuming no ties)
                                        Srk: Streak (winning or losing)""".trimIndent(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    )
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
fun StandingsTablePreview() {
    BerlinSkylarksTheme {
        StandingsTable(testTable)
    }
}