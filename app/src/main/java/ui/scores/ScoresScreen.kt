package ui.scores

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.ScoresViewModel
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresScreen(
    modifier: Modifier = Modifier,
    scoresViewModel: ScoresViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(scoresViewModel.options[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Scores") },
                /*navigationIcon = {
                    IconButton(onClick = { *//*TODO*//* }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Button"
                        )
                    }
                },*/
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Button"
                        )
                    }
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
                            value = selectedOptionText,
                            onValueChange = {},
                            label = { Text("Selected League") },
                            leadingIcon = { Icon(imageVector = Icons.Outlined.Groups, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            scoresViewModel.options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
            )
        }, content = {
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Row {
                    Button(onClick = {
                        scoresViewModel.loadGames()
                    }) {
                        Text(text = "Load games")
                    }
                    Spacer(modifier = Modifier.weight(1.0F))
                    Text(scoresViewModel.gamesCount.toString())
                }
                Text(
                    text = "Selected Season: 2022",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                Text("This is a placeholder for a segmented button once available in Compose", style = MaterialTheme.typography.labelSmall)
                var showExternalGames by remember { mutableStateOf(true) }

                Card(
                    modifier = Modifier
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.8F)
                        ) {
                            Text(text = "Show External Games", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "Controls whether the list is filtered by just Skylarks games.",
                                style = MaterialTheme.typography.labelSmall,
                                //color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Spacer(modifier = Modifier.weight(1.0F))
                        Switch(
                            modifier = Modifier.semantics { contentDescription = "Show external Games" },
                            checked = showExternalGames,
                            onCheckedChange = { showExternalGames = it },
                            colors = SwitchDefaults.colors(

                            )
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 85.dp) // height of BottomBar
                ) {
                    item { 
                        if (scoresViewModel.gamescores.isEmpty()) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 5.dp)
                                    )
                                    Text(
                                        text = "There are no games to display.",
                                        modifier = Modifier
                                            .weight(1.0F)
                                    )
                                }
                            }
                        }
                    }
                    items(scoresViewModel.gamescores) { gameScore ->
                        ScoresItem(gameScore)
                    }
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        ScoresScreen()
    }
}