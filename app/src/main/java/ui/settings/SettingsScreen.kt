package ui.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@Composable
fun SettingsScreen() {
    Text(text="Settings")
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    BerlinSkylarksTheme {
        SettingsScreen()
    }
}