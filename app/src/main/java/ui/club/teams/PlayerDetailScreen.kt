package ui.club.teams

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PlayerDetailScreen(playerID: Int) {
    Text(text = playerID.toString())
}