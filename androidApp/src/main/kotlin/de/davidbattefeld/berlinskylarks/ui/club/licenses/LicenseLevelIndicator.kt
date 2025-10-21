package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LicenseLevelIndicator(
    level: String?,
    size: Dp = 28.dp,
) {
    val bg = when (level?.uppercase()) {
        "A" -> MaterialTheme.colorScheme.primary
        "B" -> MaterialTheme.colorScheme.secondary
        "C" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.outline
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(text = (level ?: "").take(2), fontWeight = FontWeight.SemiBold)
    }
}
