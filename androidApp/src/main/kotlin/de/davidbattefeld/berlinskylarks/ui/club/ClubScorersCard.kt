package de.davidbattefeld.berlinskylarks.ui.club

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.clubCardPadding

@Composable
fun ClubScorersCard(
    scorersRoute: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.clickable { scorersRoute() }
    ) {
        Column(
            modifier = Modifier
                .padding(clubCardPadding),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Create,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(text = "Scorer", style = MaterialTheme.typography.titleLarge)
            Text(text = "Keeping statistics")
        }
    }
}