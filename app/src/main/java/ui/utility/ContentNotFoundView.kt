package ui.utility

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.global.cardPadding

@Composable
fun ContentNotFoundView(content: String) {
    Card(
        modifier = Modifier
            .padding(cardPadding)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "There are no $content to display.",
                modifier = Modifier
                    .weight(1.0F)
            )
        }
    }
}