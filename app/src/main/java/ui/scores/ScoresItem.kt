package ui.scores

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.Game

@Composable
fun ScoresItem(game: Game, modifier: Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column() {
                    Text(text = game.league.name, style = MaterialTheme.typography.titleSmall)
                    Row{
                        Text(
                            text = game.localisedDate ?: game.time,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1.0F))
                GameResultIndicator(game = game)
            }
            HorizontalDivider()
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = game.roadLogo),
                        contentDescription = "Road Team Logo",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(35.dp)
                    )
                    Text(text = game.away_team_name, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.weight(1.0F))
                    Text(text = (game.away_runs ?: "").toString(), style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if ((game.home_runs ?: 0) < (game.away_runs ?: 0)) MaterialTheme.colorScheme.onPrimaryContainer else Color.Gray,
                        fontSize = 18.sp,
                    ), modifier = Modifier.padding(end = 6.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = game.homeLogo),
                        contentDescription = "Home Team Logo",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(35.dp)
                    )
                    Text(text = game.home_team_name, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.weight(1.0F))
                    Text(text = (game.home_runs ?: "").toString(), style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if ((game.home_runs ?: 0) > (game.away_runs ?: 0)) MaterialTheme.colorScheme.onPrimaryContainer else Color.Gray,
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
           // ScoresItem(game = testGame)
        }

    }
}