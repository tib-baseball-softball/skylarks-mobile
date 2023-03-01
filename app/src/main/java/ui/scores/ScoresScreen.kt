package ui.scores

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.ScoresViewModel
import de.davidbattefeld.berlinskylarks.global.cardPadding
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoresScreen(
    modifier: Modifier = Modifier,
    scoresViewModel: ScoresViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(scoresViewModel.options[0]) }
    var showExternalGames by remember { mutableStateOf(true) }
    val tabTitles = listOf("Previous", "Current", "Next", "Any")

    //val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "Scores")
                                Spacer(modifier = Modifier.weight(1.0F))
                                Text(
                                    text = "Selected Season: ${scoresViewModel.selectedSeason}",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                )
                            }
                        },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Button"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0F))
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
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 85.dp) // height of BottomBar
                    .padding(horizontal = 10.dp)
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                item {
                    TabRow(selectedTabIndex = scoresViewModel.tabState) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = scoresViewModel.tabState == index,
                                onClick = {
                                            scoresViewModel.tabState = index
                                            scoresViewModel.load()
                                          },
                                text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .padding(cardPadding),
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
                }
                item {
                    Divider(modifier = Modifier.padding(horizontal = 12.dp))
                }
                item {
                    if (scoresViewModel.gameScores.isEmpty()) {
                        Card(
                            modifier = Modifier
                                .padding(cardPadding)
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
                items(scoresViewModel.gameScores) { gameScore ->
                    ScoresItem(gameScore)
                }
                item {
                    Row {
                        Button(onClick = {
                            scoresViewModel.load()
                        }) {
                            Text(text = "Load games")
                        }
                    }
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
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        ScoresScreen()
    }
}