package de.davidbattefeld.berlinskylarks.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.berlinskylarks.shared.data.model.tib.Media
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun NewsItemCard(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    teaser: String,
    image: Media?,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = elevation,
    ) {
        Column {
            if (image != null) {
                AsyncImage(
                    model = image.url,
                    contentDescription = image.alt,
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = teaser,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun NewsItemCardPreview() {
    BerlinSkylarksTheme {
        NewsItemCard(
            title = "Sample Game Report",
            date = "2023-09-15",
            teaser = "This is a sample game report teaser.",
            modifier = Modifier,
            image = Media(
                id = 1,
                url = "https://picsum.photos/1920/1080?random",
                alt = "Sample Image",
            ),
        )
    }
}