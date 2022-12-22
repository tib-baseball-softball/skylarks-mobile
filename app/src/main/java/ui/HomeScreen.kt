package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun HomeScreen() {
    Text(text="Home")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BerlinSkylarksTheme {
        HomeScreen()
    }
}