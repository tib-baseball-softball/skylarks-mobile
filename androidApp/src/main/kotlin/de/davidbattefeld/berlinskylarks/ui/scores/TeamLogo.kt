package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import de.davidbattefeld.berlinskylarks.R

@Composable
fun TeamLogo(
    teamLogoType: TeamLogoType,
    logo: Int,
    teamName: String,
    logoURL: String?,
    modifier: Modifier = Modifier
) {
    var fallbackLogo: Int = R.drawable.app_home_team_logo

    if (teamLogoType == TeamLogoType.ROAD) {
        fallbackLogo = R.drawable.app_road_team_logo
    }
    val fallbackPainter = painterResource(id = fallbackLogo)

    if (logo != fallbackLogo) {
        Image(
            painter = painterResource(id = logo),
            contentDescription = "$teamName Logo",
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = logoURL,
            contentDescription = "$teamName Logo",
            modifier = modifier,
            placeholder = fallbackPainter,
            error = fallbackPainter,
            fallback = fallbackPainter,
        )
    }
}

enum class TeamLogoType {
    HOME, ROAD
}