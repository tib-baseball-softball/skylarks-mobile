package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.LeagueTable

@Composable
fun StandingsLeagueRow(leagueTable: LeagueTable) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.TableChart, contentDescription = "")
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = leagueTable.league_name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${leagueTable.rows.count()} Teams", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 2.dp))
            }
            Spacer(modifier = Modifier.weight(1.0F))
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = "")
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
fun StandingsLeagueRowPreview() {
    BerlinSkylarksTheme {
        Surface() {
            StandingsLeagueRow(testTable)
        }
    }
}