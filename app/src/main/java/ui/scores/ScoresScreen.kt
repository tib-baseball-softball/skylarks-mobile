package ui.scores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Button"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Button"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Button"
                        )
                    }
                }
            )
        }, content = { padding ->
            Column(
                modifier = Modifier.padding(padding),
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
                LazyColumn {
                    items(scoresViewModel.gamescores) { gameScore ->
                        ScoresItem(gameScore)
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        ScoresScreen()
    }
}