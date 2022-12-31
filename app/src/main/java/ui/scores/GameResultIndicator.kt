package ui.scores

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.theme.skylarksRed
import model.GameScore

@Composable
fun GameResultIndicator(gameScore: GameScore) {
    val endPadding = Modifier.padding(end = 6.dp)
    if (gameScore.isDerby) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Heart Icon",
            tint = skylarksRed,
            modifier = endPadding
        )
    } else if (gameScore.isExternalGame) {
        Text(
            text = gameScore.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            modifier = endPadding
        )
    } else {
        Text(
            text = gameScore.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            color = if (gameScore.skylarksWin) Color.Green else Color.Red,
            modifier = endPadding
        )
    }
}