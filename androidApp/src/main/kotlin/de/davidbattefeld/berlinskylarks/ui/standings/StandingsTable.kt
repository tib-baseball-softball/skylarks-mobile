package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun StandingsTable(table: LeagueTable?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandingsTableHeader()
        table?.rows?.forEach { row ->
            StandingsTableRow(row)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        }
    }
}

@PreviewLightDark
@Composable
fun StandingsTablePreview() {
    BerlinSkylarksTheme {
        StandingsTable(testTable)
    }
}