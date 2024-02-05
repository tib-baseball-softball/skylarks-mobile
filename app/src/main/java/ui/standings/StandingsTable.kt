package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.LeagueTable

@Composable
fun StandingsTable(table: LeagueTable) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StandingsTableHeader()
        }
        items(table.rows) { row ->
            StandingsTableRow(row)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        }
        item {
            StandingsTableLegend()
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
fun StandingsTablePreview() {
    BerlinSkylarksTheme {
        StandingsTable(testTable)
    }
}