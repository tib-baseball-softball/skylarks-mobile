package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
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
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility.Companion.formatDateHuman
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility.Companion.relativeTime

@Composable
fun LicenseDetailHolderCard(
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
                leadingContent = { Icon(Icons.Outlined.Person, contentDescription = null) },
                headlineContent = {
                    Text(
                        "${license.person.firstName} ${license.person.lastName}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        "Holder",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.Key, contentDescription = null) },
                headlineContent = {
                    Text(
                        license.number,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        "Number",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null) },
                headlineContent = {
                    Text(
                        formatDateHuman(license.validUntil),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        "Valid until",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            ListItem(
                leadingContent = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null) },
                headlineContent = {
                    Text(
                        relativeTime(license.validUntil),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        "Relative Time",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = listItemColors
            )
        }
    }
}
