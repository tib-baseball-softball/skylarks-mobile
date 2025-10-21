package de.davidbattefeld.berlinskylarks.ui.club.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Field

@Composable
fun FieldTrafficCard(
    field: Field,
    modifier: Modifier = Modifier
) {
    val description = field.description

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!description.isNullOrBlank()) {
                // Split on double newlines for paragraphs
                val paragraphs =
                    description.split("\n\n").map { it.trim() }.filter { it.isNotEmpty() }
                paragraphs.forEachIndexed { index, para ->
                    Text(text = para.replace("\n", "\n\n"))
                    if (index < paragraphs.lastIndex) Spacer(Modifier.height(8.dp))
                }
            } else {
                Text(text = "No ballpark information available.")
            }
        }
    }
}
