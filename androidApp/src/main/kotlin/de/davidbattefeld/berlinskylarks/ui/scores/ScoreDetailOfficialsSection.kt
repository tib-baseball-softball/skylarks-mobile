package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Sports
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
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Game
import de.davidbattefeld.berlinskylarks.global.cardPadding

@Composable
fun ScoreDetailOfficialsSection(
    showOfficialsData: Boolean,
    game: Game,
) {
    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = showOfficialsData,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        Card(
            modifier = Modifier
                .padding(cardPadding),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                text = "Umpires",
                style = MaterialTheme.typography.titleMedium,
            )
            if (game.umpire_assignments.isNotEmpty()) {
                game.umpire_assignments.forEach {
                    ListItem(
                        headlineContent = { Text("${it.license.person.last_name}, ${it.license.person.first_name}") },
                        supportingContent = { Text(it.license.number) },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Sports,
                                contentDescription = "Localized description",
                            )
                        },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    )
                }
            } else {
                ListItem(
                    headlineContent = { Text("No umpires assigned yet.") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.PersonOff,
                            contentDescription = "no person found icon",
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                text = "Scorers",
                style = MaterialTheme.typography.titleMedium,
            )
            if (game.scorer_assignments.isNotEmpty()) {
                game.scorer_assignments.forEach {
                    ListItem(
                        headlineContent = { Text("${it.license.person.last_name}, ${it.license.person.first_name}") },
                        supportingContent = { Text(it.license.number) },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Create,
                                contentDescription = "scorer icon",
                            )
                        },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    )
                }
            } else {
                ListItem(
                    headlineContent = { Text("No scorers assigned yet.") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.PersonOff,
                            contentDescription = "no person found icon",
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            }
        }
    }
}