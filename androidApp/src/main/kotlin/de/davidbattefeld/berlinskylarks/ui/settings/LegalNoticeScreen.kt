package de.davidbattefeld.berlinskylarks.ui.settings

import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LegalNoticeViewModel

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun LegalNoticeScreen(
    vm: LegalNoticeViewModel,
    topLevelBackStack: de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack<androidx.navigation3.runtime.NavKey>,
    navigationType: de.davidbattefeld.berlinskylarks.ui.nav.NavigationType,
) {
    val context = LocalContext.current
    val displayedText = vm.readStaticMarkdownFile(fileName = "app_impressum_en.md", context)
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()
    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()

    val scrollBehavior =
        androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    androidx.compose.material3.Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { androidx.compose.material3.Text(text = de.davidbattefeld.berlinskylarks.ui.nav.LegalNotice.title) },
            )
        },
        snackbarHost = { de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost() },
        bottomBar = {
            de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar(
                topLevelBackStack,
                navigationType
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            item {
                AndroidView(
                    factory = {
                        TextView(it).apply {
                            text = displayedText
                            setTextColor(textColor)
                            setBackgroundColor(backgroundColor)
                        }
                    },
                )
            }
        }
    }
}