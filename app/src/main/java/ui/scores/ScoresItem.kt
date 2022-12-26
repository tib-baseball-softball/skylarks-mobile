package ui.scores

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.testdata.testGame
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.GameScore

@Composable
fun ScoresItem(gameScore: GameScore) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            // TODO: check theming
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column() {
                    Text(text = "Verbandsliga Baseball", style = MaterialTheme.typography.titleSmall)
                    Text(text = "21.8.2022, 12:00", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1.0F))
                Text(text = "W", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(end = 6.dp))
            }
            Divider()
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.sluggers_logo),
                        contentDescription = "Road Team Logo",
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(35.dp)
                    )
                    Text(text = "Sluggers", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.weight(1.0F))
                    Text(text = "4", style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ), modifier = Modifier.padding(end = 6.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.bird_outlined),
                        contentDescription = "Home Team Logo",
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(35.dp)
                    )
                    Text(text = "Skylarks", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.weight(1.0F))
                    Text(text = "7", style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ), modifier = Modifier.padding(end = 6.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun ScoresItemPreview() {
    BerlinSkylarksTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            ScoresItem(gameScore = testGame)
        }

    }
}