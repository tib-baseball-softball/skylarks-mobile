package ui.standings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.StandingsViewModel
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun StandingsScreen(
    standingsViewModel: StandingsViewModel = viewModel(),
    detailRoute: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.padding(bottom = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(clubCardPadding),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Insights,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "Club Standings", style = MaterialTheme.typography.titleLarge)
                    Text(text = "See records for all Club teams at a glance and get an overview.")
                }
            }
        }
        items(standingsViewModel.leagueGroups) {
            StandingsLeagueRow(
                leagueGroup = it,
                modifier = Modifier
                    .clickable {
                        detailRoute(it.id)
                    }
            )
        }
    }
    LaunchedEffect(Unit) {
        if (standingsViewModel.leagueGroups.isEmpty()) {
            standingsViewModel.load()
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 400,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 400
)
@Composable
fun StandingsScreenPreview() {
    BerlinSkylarksTheme {
       // StandingsScreen()
    }
}