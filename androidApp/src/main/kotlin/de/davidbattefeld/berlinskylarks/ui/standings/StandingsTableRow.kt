package de.davidbattefeld.berlinskylarks.ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.testdata.testRow
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun StandingsTableRow(row: LeagueTable.Row) {
    val textColor = if (row.team_name.contains("Skylarks", ignoreCase = true)) {
        MaterialTheme.colorScheme.primary
    } else { MaterialTheme.colorScheme.onSurface }

    Row(
        modifier = Modifier
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            row.rank,
            color = textColor,
            modifier = Modifier
                .weight(0.1F)
                .padding(4.dp)
        )
        Text(
            row.team_name,
            color = textColor,
            modifier = Modifier
                .weight(0.50F, fill = true)
                .padding(4.dp)
        )
        Text(
            row.wins_count.toInt().toString(),
            color = textColor,
            modifier = Modifier
                .weight(0.1F)
                .padding(4.dp)
        )
        Text(
            row.losses_count.toInt().toString(),
            color = textColor,
            modifier = Modifier
                .weight(0.1F)
                .padding(4.dp)
        )
        Text(
            row.quota,
            color = textColor,
            modifier = Modifier
                .weight(0.18F)
                .padding(4.dp)
        )
        Text(
            row.games_behind,
            color = textColor,
            modifier = Modifier
                .weight(0.12F)
                .padding(4.dp)
        )
        Text(
            row.streak,
            color = textColor,
            modifier = Modifier
                .weight(0.15F)
                .padding(4.dp)
        )
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
fun StandingsTableRowPreview() {
    BerlinSkylarksTheme {
        Surface() {
            StandingsTableRow(testRow)
        }
    }
}