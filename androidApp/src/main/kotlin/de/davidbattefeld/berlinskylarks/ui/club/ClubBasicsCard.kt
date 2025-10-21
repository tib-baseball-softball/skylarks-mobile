package de.davidbattefeld.berlinskylarks.ui.club

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Club
import de.davidbattefeld.berlinskylarks.R

@Composable
fun ClubBasicsCard(
    club: Club,
    modifier: Modifier = Modifier
) {
    val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_rondell),
                    contentDescription = "Skylarks Club Logo",
                    modifier = Modifier.height(80.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.Info, contentDescription = null) },
                headlineContent = { Text(text = club.name, style = MaterialTheme.typography.bodyLarge) },
                supportingContent = { Text("Name", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.Badge, contentDescription = null) },
                headlineContent = { Text(text = "${club.acronym} / 0${club.organizationId} ${club.number}", style = MaterialTheme.typography.bodyLarge) },
                supportingContent = { Text("Acronym / IDs", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.Shield, contentDescription = null) },
                headlineContent = { Text(text = club.mainClub, style = MaterialTheme.typography.bodyLarge) },
                supportingContent = { Text("Main Club", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                colors = listItemColors
            )
        }
    }
}
