package ui.club.teams

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TeamDetailScreen(teamID: Int) {
    Text(text = teamID.toString())
}