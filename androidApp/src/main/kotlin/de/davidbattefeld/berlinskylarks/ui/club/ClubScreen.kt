package de.davidbattefeld.berlinskylarks.ui.club

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.cardGridSpacing
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.club.functionary.FunctionaryCard
import de.davidbattefeld.berlinskylarks.ui.nav.Club
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ClubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubScreen(
    vm: ClubViewModel,
    teamsRoute: () -> Unit,
    functionaryRoute: () -> Unit,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val club by vm.club.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = Club.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
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
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Badge,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "Berlin Skylarks Baseball & Softball Club")
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tag,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "0${club?.organizationId} ${club?.number}")
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(text = club?.mainClub ?: "None")
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                // If nesting lazy components, they cannot be of infinite height
                modifier = Modifier
                    .height(380.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(cardGridSpacing),
                horizontalArrangement = Arrangement.spacedBy(cardGridSpacing)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(text = "Details", style = MaterialTheme.typography.titleLarge)
                            Text(text = "More Info")
                        }
                    }
                }
                item {
                    ClubTeamsCard(teamsRoute)
                }
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Create,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Text(text = "Scorer", style = MaterialTheme.typography.titleLarge)
                            Text(text = "Keeping statistics")
                        }
                    }
                }
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Sports,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Text(text = "Umpire", style = MaterialTheme.typography.titleLarge)
                            Text(text = "Our referees")
                        }
                    }
                }
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(clubCardPadding),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Stadium,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Text(text = "Ballpark", style = MaterialTheme.typography.titleLarge)
                            Text(text = "The Larks' Nest")
                        }
                    }
                }
                item {
                    FunctionaryCard(functionaryRoute)
                }
            }
        }
    }
}

/*
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
}*/
