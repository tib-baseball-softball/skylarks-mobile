package de.davidbattefeld.berlinskylarks.ui.scores.gamereport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import kotlin.time.Instant

@Composable
fun ScoresDetailGameReportSection(
    show: Boolean,
    gameReport: GameReport?,
) {
    AnimatedVisibility(
        modifier = Modifier.padding(12.dp),
        visible = show,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        if (gameReport == null) {
            ContentNotFoundView("game reports")
        } else {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(gameReport.title, style = MaterialTheme.typography.titleLarge)

                OutlinedCard {
                    Column(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = "",
                            )
                            Text("Published:", fontWeight = FontWeight.SemiBold)
                            Text(AndroidDateTimeUtility.formatDate(Instant.parse(gameReport.date)))
                        }
                        HorizontalDivider()
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Leaderboard,
                                contentDescription = "",
                            )
                            Text("League:", fontWeight = FontWeight.SemiBold)
                            Text(gameReport.league?.name ?: "No League")
                        }
                        HorizontalDivider()
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "",
                            )
                            Text("Author:", fontWeight = FontWeight.SemiBold)
                            Text(gameReport.author)
                        }
                    }
                }

                gameReport.teaserImage.forEach { image ->
                    AsyncImage(
                        model = image.url,
                        contentDescription = image.alt,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                Text(
                    text = "Report Game 1",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(gameReport.reportFirstPlain)

                gameReport.reportSecondPlain?.let { reportSecond ->
                    HorizontalDivider()
                    Text(
                        text = "Report Game 2",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(reportSecond)
                }

                gameReport.previewPlain?.let { preview ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Text(
                                text = "Preview",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                            Text(preview)
                        }
                    }
                }

                gameReport.gallery?.let { gallery ->
                    Text("Gallery", style = MaterialTheme.typography.titleMedium)
                    gallery.forEach { image ->
                        AsyncImage(
                            model = image.url,
                            contentDescription = image.alt,
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }
    }
}