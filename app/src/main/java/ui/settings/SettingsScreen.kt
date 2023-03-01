package ui.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.SettingsViewModel
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
          MediumTopAppBar(
              title = { Text(text = "Settings") }
          )
        }, content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Row() {
                    Text(text = "stuff")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        TextField(
                            // The `menuAnchor` modifier must be passed to the text field for correctness.
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = settingsViewModel.selectedSeason.toString(),
                            onValueChange = {},
                            label = { Text("Selected Season") },
                            leadingIcon = { Icon(imageVector = Icons.Outlined.Groups, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            settingsViewModel.possibleSeasons.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.toString()) },
                                    onClick = {
                                        settingsViewModel.selectedSeason = selectionOption
                                        expanded = false
                                        settingsViewModel.writeSelectedSeason(selectionOption)
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
                Column() {
                    Text(text = "Selected Season is:")
                    Text(text = settingsViewModel.selectedSeason.toString())
                }
            }
        }
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
fun SettingsScreenPreview() {
    BerlinSkylarksTheme {
        SettingsScreen()
    }
}