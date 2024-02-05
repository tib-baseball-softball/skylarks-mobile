package ui.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.clubCardPadding

@Composable
fun StandingsTableLegend() {
    Card(
        modifier = Modifier.padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(clubCardPadding),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(text = "Legend:", style = MaterialTheme.typography.labelLarge)
            Text(
                text = """
                                        W: Wins
                                        L: Losses
                                        %: Winning Percentage
                                        GB: Games Behind (first place, assuming no ties)
                                        Srk: Streak (winning or losing)""".trimIndent(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}