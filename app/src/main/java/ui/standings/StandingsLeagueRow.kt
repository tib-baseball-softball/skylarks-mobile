package ui.standings

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import model.LeagueGroup

@Composable
fun StandingsLeagueRow(
    leagueGroup: LeagueGroup,
    modifier: Modifier,
) {
    ListItem(
        headlineContent = { Text(leagueGroup.name) },
        supportingContent = { Text(leagueGroup.acronym) },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.TableChart,
                contentDescription = "team icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "click to show more info",
            )
        },
        modifier = modifier,
    )
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
fun StandingsLeagueRowPreview() {
    BerlinSkylarksTheme {
        Surface() {
           // StandingsLeagueRow(testTable)
        }
    }
}