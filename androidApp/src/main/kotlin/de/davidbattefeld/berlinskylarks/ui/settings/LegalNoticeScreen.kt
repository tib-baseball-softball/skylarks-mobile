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
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.SettingsViewModel

@Composable
fun LegalNoticeScreen(vm: SettingsViewModel = viewModel()) {
    val context = LocalContext.current
    val displayedText = vm.readStaticMarkdownFile(fileName = "app_impressum_en.md", context)
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()
    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()

    LazyColumn(
        modifier = Modifier
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