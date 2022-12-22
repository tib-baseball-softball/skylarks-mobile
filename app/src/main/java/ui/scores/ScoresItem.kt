package ui.scores

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun ScoresItem() {
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column() {
                Text(text = "Verbandsliga Baseball", style = MaterialTheme.typography.bodySmall)
                Text(text = "21.8.2022, 12:00", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1.0F))
            Text(text = "W", style = MaterialTheme.typography.headlineSmall)
        }
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "Road Team Logo",
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(text = "Sluggers", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1.0F))
                Text(text = "4", style = MaterialTheme.typography.titleLarge)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "Home Team Logo",
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(text = "Skylarks", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1.0F))
                Text(text = "7", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ScoresItemPreview() {
    BerlinSkylarksTheme {
        ScoresItem()
    }
}