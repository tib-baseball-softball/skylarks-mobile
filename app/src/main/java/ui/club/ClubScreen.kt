package ui.club

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.cardGridSpacing
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = "Club") },
                //colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)),
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)  // no clue how this works, but breaks without it #Classic
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight(),
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_rondell),
                    contentDescription = "Skylarks Club Logo",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                )
                Card(
                    modifier = Modifier.padding(clubCardPadding),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Badge, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text(text = "Berlin Skylarks Baseball & Softball Club")
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Tag, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text(text = "09 01100")
                    }
                    Divider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Shield, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text(text = "Turngemeinde in Berlin e.V.")
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    // If nesting lazy components, they cannot be of infinite height
                    modifier = Modifier
                        .height(600.dp)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(cardGridSpacing),
                    horizontalArrangement = Arrangement.spacedBy(cardGridSpacing)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Info, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                                Text(text = "Details", style = MaterialTheme.typography.titleLarge)
                                Text(text = "More Info")
                            }
                        }
                    }
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Groups, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                                Text(text = "Teams", style = MaterialTheme.typography.titleLarge)
                                Text(text = ("Adult and Youth"))
                            }
                        }
                    }
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Create, contentDescription = "", tint = MaterialTheme.colorScheme.tertiary)
                                Text(text = "Scorer", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Keeping statistics")
                            }
                        }
                    }
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Sports, contentDescription = "", tint = MaterialTheme.colorScheme.tertiary)
                                Text(text = "Umpire", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Our referees")
                            }
                        }
                    }
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Stadium, contentDescription = "", tint = MaterialTheme.colorScheme.secondary)
                                Text(text = "Ballpark", style = MaterialTheme.typography.titleLarge)
                                Text(text = "The Larks' Nest")
                            }
                        }
                    }
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(clubCardPadding),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.People, contentDescription = "", tint = MaterialTheme.colorScheme.secondary)
                                Text(text = "Officials", style = MaterialTheme.typography.titleLarge)
                                Text(text = "Administration")
                            }
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
fun ClubScreenPreview() {
    BerlinSkylarksTheme {
        ClubScreen()
    }
}