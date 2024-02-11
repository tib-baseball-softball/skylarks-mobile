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
import model.Game

@Composable
fun GameResultIndicator(game: Game) {
    val endPadding = Modifier.padding(end = 6.dp)
    var color = MaterialTheme.colorScheme.onSurface

    if (!game.isExternalGame && (game.human_state != "geplant" && game.human_state != "ausgefallen")) {
        color = if (game.skylarksWin) Color.Green else Color.Red
    }

    if (game.isDerby) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Heart Icon",
            tint = skylarksRed,
            modifier = endPadding
        )
    } else if (game.isExternalGame) {
        Text(
            text = game.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            modifier = endPadding
        )
    } else {
        Text(
            text = game.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            color = color,
            modifier = endPadding
        )
    }
}