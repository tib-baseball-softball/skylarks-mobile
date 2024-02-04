package ui.scores

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ScoresDetailMainLogoSection(logo: Int, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = logo),
            contentDescription = "Road Team Logo",
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
        )
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}