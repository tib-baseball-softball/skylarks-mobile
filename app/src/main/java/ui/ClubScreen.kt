package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun ClubScreen() {
    Text(text="Club")
}

@Preview(showBackground = true)
@Composable
fun ClubScreenPreview() {
    BerlinSkylarksTheme {
        ClubScreen()
    }
}