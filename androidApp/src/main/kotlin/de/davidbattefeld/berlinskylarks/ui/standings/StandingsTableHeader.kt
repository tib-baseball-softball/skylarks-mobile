package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StandingsTableHeader() {
    val headerStyle = MaterialTheme.typography.titleMedium

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
                .padding(4.dp)
        )
        Text(
            "Team",
            style = headerStyle,
            modifier = Modifier
                .weight(0.55F, fill = true)
                .padding(4.dp)
        )
        Text(
            "W",
            style = headerStyle,
            modifier = Modifier
                .weight(0.15F)
                .padding(4.dp)
        )
        Text(
            "L",
            style = headerStyle,
            modifier = Modifier
                .weight(0.15F)
                .padding(4.dp)
        )
        Text(
            "%",
            style = headerStyle,
            modifier = Modifier
                .weight(0.15F)
                .padding(4.dp)
        )
        Text(
            "GB",
            style = headerStyle,
            modifier = Modifier
                .weight(0.15F)
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