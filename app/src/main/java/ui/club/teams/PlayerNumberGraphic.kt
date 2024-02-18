package ui.club.teams

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PlayerNumberGraphic(content: String, colors: Pair<Color, Color>) {
    Text(
        text = content,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = colors.second,
        modifier = Modifier
            .padding(16.dp)
            .drawBehind {
                drawCircle(
                    color = colors.first,
                    radius = (this.size.maxDimension * 0.75).toFloat()
                )
            }
            .widthIn(25.dp)
    )
}