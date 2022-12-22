package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun ScoresScreen() {
    Text(text = "Scores")
}

@Preview(showBackground = true)
@Composable
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        ScoresScreen()
    }
}