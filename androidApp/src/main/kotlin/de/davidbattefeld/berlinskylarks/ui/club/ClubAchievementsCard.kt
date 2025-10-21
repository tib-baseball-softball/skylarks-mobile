package de.davidbattefeld.berlinskylarks.ui.club

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Club

@Composable
fun ClubAchievementsCard(
    club: Club,
    modifier: Modifier = Modifier
) {
    val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ListItem(
                leadingContent = { Icon(Icons.Outlined.EmojiEvents, contentDescription = null) },
                headlineContent = { Text("Achievements", style = MaterialTheme.typography.titleMedium) },
                colors = listItemColors
            )
            val text = club.successes.ifBlank { "No achievements listed." }
            Text(text = text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
        }
    }
}
