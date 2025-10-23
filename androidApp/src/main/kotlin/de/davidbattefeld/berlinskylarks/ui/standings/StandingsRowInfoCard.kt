package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.Signpost
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import de.davidbattefeld.berlinskylarks.ui.utility.InfoRowWithIcon

@Composable
fun StandingsRowInfoCard(
    name: String,
    acronym: String,
    record: String,
    percentage: String,
    rank: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(text = name, style = MaterialTheme.typography.titleMedium)
            }
            InfoRowWithIcon(
                icon = Icons.Outlined.Signpost,
                label = "Acronym",
                value = acronym,
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.Analytics,
                label = "Record",
                value = record,
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.Percent,
                label = "Percentage",
                value = percentage,
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.Tag,
                label = "Rank",
                value = rank,
                iconColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TeamInfoCardPreview() {
    BerlinSkylarksTheme {
        StandingsRowInfoCard(
            name = "Boston Beer League",
            acronym = "TEA",
            record = "12 - 4",
            percentage = ".376",
            rank = "3.",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
        )
    }
}