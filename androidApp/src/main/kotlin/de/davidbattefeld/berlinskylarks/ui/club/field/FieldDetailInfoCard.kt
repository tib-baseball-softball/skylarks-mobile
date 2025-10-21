package de.davidbattefeld.berlinskylarks.ui.club.field

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.ReceiptLong
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Field

@Composable
fun FieldDetailInfoCard(
    field: Field,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val listItemColors = ListItemDefaults.colors(
        containerColor = Color.Transparent
    )

    val addressLine = listOfNotNull(field.street, listOfNotNull(field.postalCode, field.city).filter { it.isNotBlank() }.joinToString(" ").ifBlank { null }).joinToString(", ")

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ListItem(
                headlineContent = {
                    Text(field.name, style = MaterialTheme.typography.titleMedium)
                },
                supportingContent = { Text("Ballpark Name", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingContent = { Icon(imageVector = Icons.Outlined.ReceiptLong, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                colors = listItemColors
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            if (field.addressAddon.isNotBlank()) {
                ListItem(
                    headlineContent = {
                        Text(field.addressAddon, style = MaterialTheme.typography.bodyLarge)
                    },
                    supportingContent = { Text("Address Addon", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    leadingContent = { Icon(imageVector = Icons.Outlined.Info, contentDescription = null) },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }

            val total = field.spectatorTotal
            if (total != null) {
                ListItem(
                    headlineContent = { Text(total.toString(), style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Capacity", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    leadingContent = { Icon(imageVector = Icons.Outlined.People, contentDescription = null) },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }

            val seats = field.spectatorSeats
            if (seats != null) {
                ListItem(
                    headlineContent = { Text(seats.toString(), style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Seats", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    leadingContent = { Icon(imageVector = Icons.Outlined.ReceiptLong, contentDescription = null) },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }

            if (addressLine.isNotBlank()) {
                ListItem(
                    modifier = Modifier.clickable {
                        val q = Uri.encode(listOfNotNull(field.name, field.street, field.postalCode, field.city).joinToString(", "))
                        val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$q")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    headlineContent = { Text(addressLine, style = MaterialTheme.typography.bodyLarge) },
                    supportingContent = { Text("Address", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    leadingContent = { Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary) },
                    colors = listItemColors
                )
            }
        }
    }
}
