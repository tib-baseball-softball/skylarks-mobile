package de.davidbattefeld.berlinskylarks.ui.scores

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
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.ui.theme.skylarksRed

@Composable
fun GameResultIndicator(gameDecorator: GameDecorator) {
    val endPadding = Modifier.padding(end = 6.dp)
    var color = MaterialTheme.colorScheme.onSurface

    if (!gameDecorator.isExternalGame && (gameDecorator.game.humanState != "geplant" && gameDecorator.game.humanState != "ausgefallen")) {
        color = if (gameDecorator.skylarksWin) Color.Green else Color.Red
    }

    if (gameDecorator.isDerby) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Heart Icon",
            tint = skylarksRed,
            modifier = endPadding
        )
    } else if (gameDecorator.isExternalGame) {
        Text(
            text = gameDecorator.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            modifier = endPadding
        )
    } else {
        Text(
            text = gameDecorator.getGameResultIndicatorText(),
            style = MaterialTheme.typography.titleMedium,
            color = color,
            modifier = endPadding
        )
    }
}