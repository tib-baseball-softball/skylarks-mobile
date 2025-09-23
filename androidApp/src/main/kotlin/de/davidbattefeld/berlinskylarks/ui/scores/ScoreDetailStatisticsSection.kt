package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Tag
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.berlinskylarks.shared.data.model.Game
import de.davidbattefeld.berlinskylarks.global.cardPadding

@Composable
fun ScoreDetailStatisticsSection(
    showStatisticsData: Boolean,
    game: Game,
) {
    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = showStatisticsData,
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
                text = "Statistics",
                style = MaterialTheme.typography.titleMedium,
            )
            ListItem(
                headlineContent = { Text("${game.league.name} (${game.league.acronym})") },
                supportingContent = { Text("League") },
                leadingContent = {
                    Icon(
                        Icons.Filled.TableChart,
                        contentDescription = "league icon",
                    )
                },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            ListItem(
                headlineContent = { Text(game.matchID) },
                supportingContent = { Text("Match ID") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Tag,
                        contentDescription = "ID icon",
                    )
                },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            ListItem(
                headlineContent = { Text(game.plannedInnings?.toString() ?: "") },
                supportingContent = { Text("Planned innings") },
                leadingContent = {
                    Icon(
                        Icons.Filled.HourglassEmpty,
                        contentDescription = "Localized description",
                    )
                },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            )
            if (!game.scoresheetURL.isNullOrEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    withLink(
                                        link = LinkAnnotation.Url(
                                            url = game.scoresheetURL ?: ""
                                        )
                                    ) {
                                        append("Link to Scoresheet")
                                    }
                                }
                            }
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Link,
                            contentDescription = "Localized description",
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            } else {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                ListItem(
                    headlineContent = { Text("No scoresheet available.") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Link,
                            contentDescription = "Localized description",
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            }
        }
    }
}