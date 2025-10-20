package de.davidbattefeld.berlinskylarks.ui.utility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InfoRowWithIcon(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
        Text(
            text = label,
            modifier = Modifier.weight(0.3f),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Normal

        )
        Text(
            text = value,
            modifier = Modifier.weight(0.7f),
            style = MaterialTheme.typography.labelLarge
        )
    }
}