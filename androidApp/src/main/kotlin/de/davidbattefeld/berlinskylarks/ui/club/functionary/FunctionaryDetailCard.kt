package de.davidbattefeld.berlinskylarks.ui.club.functionary

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import de.berlinskylarks.shared.data.model.Functionary
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = parser.parse(dateString)
        date?.let { formatter.format(it) } ?: dateString
    } catch (e: Exception) {
        Log.e("FunctionaryDetailScreen", "Error formatting date: $e")
        dateString
    }
}

@Composable
fun FunctionaryDetailScreen(
    functionary: Functionary,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var formattedAdmissionDate: String by remember { mutableStateOf(functionary.admission_date) }

    LaunchedEffect(functionary.admission_date) {
        formattedAdmissionDate = formatDate(functionary.admission_date)
    }

    val listItemColors = ListItemDefaults.colors(
        containerColor = Color.Transparent // Make ListItems blend with Card background
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                ListItem(
                    headlineContent = {
                        Text(
                            functionary.function,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingContent = {
                        Text(
                            "Title",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingContent = { FunctionaryIcon(functionary = functionary) },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            functionary.category,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingContent = {
                        Text(
                            "Category",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Category,
                            contentDescription = "Category"
                        )
                    },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            "${functionary.person.lastName}, ${functionary.person.firstName}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingContent = {
                        Text(
                            "Name",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingContent = { Icon(Icons.Filled.Person, contentDescription = "Name") },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            formattedAdmissionDate,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    supportingContent = {
                        Text(
                            "Since",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.CalendarToday,
                            contentDescription = "Since"
                        )
                    },
                    colors = listItemColors
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:${functionary.mail}".toUri()
                    }
                    context.startActivity(intent)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            ListItem(
                modifier = Modifier.padding(12.dp),
                supportingContent = { Text("Contact") },
                headlineContent = { Text(functionary.mail) },
                leadingContent = { Icon(Icons.Filled.Email, contentDescription = "Contact") }
            )
        }
    }
}
