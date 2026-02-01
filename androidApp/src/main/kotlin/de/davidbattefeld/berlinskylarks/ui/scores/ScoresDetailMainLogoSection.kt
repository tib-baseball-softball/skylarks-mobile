package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoresDetailMainLogoSection(
    logo: Int,
    teamLogoType: TeamLogoType,
    teamName: String,
    logoURL: String?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TeamLogo(
            teamLogoType = teamLogoType,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp),
            logo = logo,
            logoURL = logoURL,
            teamName = teamName
        )
    }
}