package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.AdditionalStat

@Composable
fun LabeledStatRow(label: String, entries: List<AdditionalStat>) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = formatAdditionalEntries(entries), modifier = Modifier.padding(start = 4.dp))
    }
}

fun formatAdditionalEntries(entries: List<AdditionalStat>): String {
    return entries.joinToString(
        separator = ", ",
    ) { entry ->
        val firstInitial = entry.person.firstName.firstOrNull()?.let { "$it." } ?: ""
        val countSuffix = if (entry.count > 1) " (${entry.count})" else ""
        "${entry.person.lastName} $firstInitial$countSuffix"
    }
}