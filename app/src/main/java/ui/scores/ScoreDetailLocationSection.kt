package ui.scores

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.davidbattefeld.berlinskylarks.classes.service.LocationService
import de.davidbattefeld.berlinskylarks.global.cardPadding
import model.Game

@Composable
fun ScoreDetailLocationSection(
    showLocationData: Boolean,
    game: Game,
) {
    val uriHandler = LocalUriHandler.current

    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = showLocationData,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        Card(
            modifier = Modifier
                .padding(cardPadding),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                text = "Location",
                style = MaterialTheme.typography.titleMedium,
            )
            ListItem(
                headlineContent = { Text(game.field?.name ?: "No field provided.") },
                supportingContent = { Text("Ballpark") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Stadium,
                        contentDescription = "ballpark icon",
                    )
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            ListItem(
                headlineContent = { Text(game.field?.street ?: "No address provided.") },
                supportingContent = { Text("Address") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "address icon",
                    )
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            ListItem(
                headlineContent = { Text("${game.field?.city} (${game.field?.postal_code})") },
                supportingContent = { Text("City") },
                leadingContent = {
                    Icon(
                        Icons.Filled.LocationCity,
                        contentDescription = "city icon",
                    )
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            ListItem(
                headlineContent = { ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        )
                        ) {
                            append("Open in Google Maps")
                        }
                    }
                ) {
                    uriHandler.openUri(LocationService.buildGoogleMapsURL(game))
                } },
                leadingContent = {
                    Icon(
                        Icons.Filled.Map,
                        contentDescription = "Localized description",
                    )
                }
            )
        }
    }
}