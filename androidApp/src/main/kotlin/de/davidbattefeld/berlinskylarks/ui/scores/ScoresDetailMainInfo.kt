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
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.global.cardPadding

@Composable
fun ScoresDetailMainInfo(gameDecorator: GameDecorator) {
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
            GameResultIndicator(gameDecorator = gameDecorator)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoresDetailMainLogoSection(
                logo = gameDecorator.roadLogo,
            )
            if (gameDecorator.game.awayRuns != null && gameDecorator.game.homeRuns != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${gameDecorator.game.awayRuns.toString()} - ${gameDecorator.game.homeRuns.toString()}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            ScoresDetailMainLogoSection(logo = gameDecorator.homeLogo)
        }
        Row(
            modifier = Modifier
                .offset(y = (-10).dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = gameDecorator.game.awayTeamName, modifier = Modifier.widthIn(max = 120.dp))
            Text(text = gameDecorator.game.homeTeamName, modifier = Modifier.widthIn(max = 120.dp))
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
                Text(text = gameDecorator.localisedDate ?: "No game date available")
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
                Text(text = gameDecorator.game.humanState)
            }
        }
    }
}