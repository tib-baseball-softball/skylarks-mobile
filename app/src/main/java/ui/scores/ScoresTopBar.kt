package ui.scores

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.readInt

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScoresTopBar(title: String, scrollBehavior: TopAppBarScrollBehavior) {
    val selectedSeason = LocalContext.current.readInt("season")
        .collectAsState(initial = Calendar.getInstance().get(Calendar.YEAR))

    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = title)
                Spacer(modifier = Modifier.weight(1.0F))
                Text(
                    text = "Selected Season: ${selectedSeason.value}",
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
        }
    )
}