package ui.calendar

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PermissionNotGrantedView() {
    val context = LocalContext.current

    Icon(
        Icons.Rounded.CalendarMonth,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "Calendar permission not granted",
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(Modifier.height(4.dp))
    Text("This is required in order for the app to save games to calendars")

    Row {
        Spacer(Modifier.weight(1.0F))
        Button(
            modifier = Modifier
                .padding(16.dp),
            onClick = {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts(
                            "package",
                            context.packageName,
                            null
                        )
                    }
                context.startActivity(intent)
            }) {
            Text("Go to settings")
        }
    }

}