package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(imageVector = Icons.Outlined.TableChart, contentDescription = "")
            Text(text = leagueTable.league_name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 5.dp))
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