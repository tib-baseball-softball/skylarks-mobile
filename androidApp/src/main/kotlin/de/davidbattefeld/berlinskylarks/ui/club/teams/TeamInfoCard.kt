package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.SportsBaseball
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.Card
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
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import de.davidbattefeld.berlinskylarks.ui.utility.InfoRowWithIcon

@Composable
fun TeamInfoCard(
    team: SkylarksTeam,
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
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                Text(text = "Team Name", style = MaterialTheme.typography.titleMedium)
            }
            InfoRowWithIcon(
                icon = Icons.Outlined.Group,
                label = "Name",
                value = team.name
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.Tag,
                label = "Acronym",
                value = team.bsmShortName ?: "None"
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.SportsBaseball,
                label = "Sport",
                value = team.sport.replaceFirstChar { it.uppercase() }
            )
            InfoRowWithIcon(
                icon = Icons.Outlined.Group,
                label = "Age Group",
                value = team.ageGroup.replaceFirstChar { it.uppercase() }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TeamInfoCardPreview() {
    BerlinSkylarksTheme {
        TeamInfoCard(team = SkylarksTeam(
            id = 0,
            name = "Test Team",
            bsmShortName = "TST",
            sport = "Test Sport",
            ageGroup = "Test Age Group",
            leagueID = 0,
        ))
    }
}