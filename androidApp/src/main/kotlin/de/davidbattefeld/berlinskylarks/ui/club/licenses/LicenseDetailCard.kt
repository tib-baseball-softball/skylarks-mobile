package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.SportsBaseball
import androidx.compose.material.icons.outlined.SportsHandball
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.License

@Composable
fun LicenseDetailCard(
    license: License,
    modifier: Modifier = Modifier
) {
    val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ListItem(
                leadingContent = { LicenseLevelIndicator(level = license.level) },
                headlineContent = { Text(text = license.level, style = MaterialTheme.typography.bodyLarge) },
                supportingContent = { Text("Level", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                colors = listItemColors
            )

            if (license.baseball == true) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.SportsBaseball, contentDescription = null) },
                    headlineContent = { Text("yes", style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Baseball", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    colors = listItemColors
                )
            }

            if (license.softball == true) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.SportsHandball, contentDescription = null) },
                    headlineContent = { Text("yes", style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Softball", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    colors = listItemColors
                )
            }

            license.sleeveNumber?.let { num ->
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    leadingContent = { Icon(Icons.Outlined.EmojiEvents, contentDescription = null) },
                    headlineContent = { Text(num.toString(), style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Sleeve Number", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    colors = listItemColors
                )
            }
        }
    }
}
