package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.berlinskylarks.shared.data.model.tib.Player
import de.davidbattefeld.berlinskylarks.R

@Composable
fun PlayerHeaderSection(player: Player) {
    val colors = when (player.isCoach()) {
        true -> Pair(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
        false -> Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
    }

    val fallbackImage = painterResource(id = R.drawable.team_placeholder_white)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp)
    ) {
        if (player.media.isNotEmpty()) {
            AsyncImage(
                model = player.media.first().url,
                contentDescription = player.media.firstOrNull()?.alt,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = fallbackImage,
                error = fallbackImage,
                fallback = fallbackImage,
            )
        } else {
            Image(
                painter = fallbackImage,
                contentDescription = "Player placeholder image"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            PlayerNumberGraphic(content = player.number, colors = colors)
            Text(text = player.fullName, style = MaterialTheme.typography.titleLarge)
        }
    }
}
