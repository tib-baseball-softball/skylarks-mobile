package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandingsScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = "Standings") },
                //colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)),
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(it)  // no clue how this works, but breaks without it #Classic
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.padding(bottom = 20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Insights, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                            Text(text = "Club Standings", style = MaterialTheme.typography.titleLarge)
                            Text(text = "See records for all Club teams at a glance and get an overview.")
                        }
                    }
                }
                val previewTables = listOf(testTable, testTable, testTable, testTable, testTable, testTable)
                items(previewTables) { leagueTable->
                    StandingsLeagueRow(leagueTable)
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
fun StandingsScreenPreview() {
    BerlinSkylarksTheme {
        StandingsScreen()
    }
}