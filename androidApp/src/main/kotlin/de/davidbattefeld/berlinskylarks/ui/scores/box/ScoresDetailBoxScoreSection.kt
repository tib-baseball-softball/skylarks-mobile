package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.MatchBoxScore

@Composable
fun ScoresDetailBoxScoreSection(
    show: Boolean,
    boxScore: MatchBoxScore?,
) {
    AnimatedVisibility(
        modifier = Modifier.padding(12.dp),
        visible = show,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        val box = boxScore
        if (box == null) {
            Text("Boxscore not available.")
        } else {
            BoxScoreContent(box)
        }
    }
}

@Composable
private fun BoxScoreContent(box: MatchBoxScore) {
    val awayTeamName = box.linescore.away.leagueEntry.team.name
    val homeTeamName = box.linescore.home.leagueEntry.team.name

    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(text = "Linescore")
        LinescoreTable(linescore = box.linescore)

        SectionHeader(text = "Offensive")
        OffensiveTable(teamName = awayTeamName, matchStats = box.offensiveAway)
        AdditionalStatsSection(stats = box.additionalAway)

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        OffensiveTable(teamName = homeTeamName, matchStats = box.offensiveHome)
        AdditionalStatsSection(stats = box.additionalHome)

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        SectionHeader(text = "Pitching")
        PitchingTable(teamName = awayTeamName, matchStats = box.pitchingAway)
        Spacer(modifier = Modifier.height(8.dp))
        PitchingTable(teamName = homeTeamName, matchStats = box.pitchingHome)
    }
}