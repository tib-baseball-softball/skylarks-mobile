package ui.club

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.clubCardPadding
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubScreen() {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Club") }
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
                    //.fillMaxHeight(),
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_rondell),
                    contentDescription = "Skylarks Club Logo",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                )
                Card(
                    modifier = Modifier.padding(clubCardPadding)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Badge, contentDescription = "")
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
                        Icon(imageVector = Icons.Outlined.Badge, contentDescription = "")
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
                        Icon(imageVector = Icons.Outlined.Badge, contentDescription = "")
                        Text(text = "Turngemeinde in Berlin e.V.")
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    // If nesting lazy components, they cannot be of infinite height
                    modifier = Modifier.height(200.dp)
                ) {
                    item { Text(text = "Stuff") }
                    item { Text(text = "Stuff") }
                    item { Text(text = "Stuff") }
                    item { Text(text = "Stuff") }
                    item { Text(text = "Stuff") }
                    item { Text(text = "Stuff") }
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