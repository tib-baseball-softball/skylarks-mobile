package de.davidbattefeld.berlinskylarks.ui.utility

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.LocalSnackbarHostState

@Composable
fun SkylarksSnackbarHost() {
    SnackbarHost(LocalSnackbarHostState.current) { data ->
        Snackbar(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(data.visuals.message, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}