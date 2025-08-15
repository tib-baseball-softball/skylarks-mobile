package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.cardPadding
import de.davidbattefeld.berlinskylarks.data.model.Game

@Composable
fun ScoresDetailMainInfo(game: Game) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Main Info",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            GameResultIndicator(game = game)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoresDetailMainLogoSection(
                logo = game.roadLogo,
            )
            if (game.away_runs != null && game.home_runs != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${game.away_runs.toString()} - ${game.home_runs.toString()}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            ScoresDetailMainLogoSection(logo = game.homeLogo)
        }
        Row(
            modifier = Modifier
                .offset(y = (-10).dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = game.away_team_name, modifier = Modifier.widthIn(max = 120.dp))
            Text(text = game.home_team_name, modifier = Modifier.widthIn(max = 120.dp))
        }
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 110.dp)
                .padding(cardPadding),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Date", style = MaterialTheme.typography.bodyMedium)
                Text(text = game.localisedDate ?: "No game date available")
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 110.dp)
                .padding(cardPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                //horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Status", style = MaterialTheme.typography.bodyMedium)
                Text(text = game.human_state)
            }
        }
    }
}