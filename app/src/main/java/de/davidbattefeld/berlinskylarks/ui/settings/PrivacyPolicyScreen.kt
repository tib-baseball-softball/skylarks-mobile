package de.davidbattefeld.berlinskylarks.ui.settings

import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.SettingsViewModel

@Composable
fun PrivacyPolicyScreen(vm: SettingsViewModel = viewModel()) {
    val displayedText = vm.readStaticMarkdownFile("app_pp_en.md")

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp),
    ) {
        item {
            AndroidView(
                factory = { TextView(it) },
                update = { it.text = displayedText }
            )
        }
    }
}