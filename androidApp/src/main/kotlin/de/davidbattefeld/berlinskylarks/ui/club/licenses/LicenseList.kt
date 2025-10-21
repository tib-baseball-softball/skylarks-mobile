package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.License

@Composable
fun LicenseList(
    licenses: List<License>,
    showSoftballSection: Boolean,
    onLicenseClick: (License) -> Unit,
    modifier: Modifier = Modifier
) {
    val baseballLicenses = licenses.filter { it.baseball == true }
    val softballLicenses = licenses.filter { it.softball == true }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            if (showSoftballSection) {
                Text(
                    text = "Baseball",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    if (baseballLicenses.isEmpty()) {
                        Text(
                            text = "No baseball licenses available.",
                            modifier = Modifier.padding(12.dp)
                        )
                    } else {
                        baseballLicenses.forEachIndexed { index, license ->
                            LicenseRow(license = license, onClick = onLicenseClick)
                            if (index < baseballLicenses.lastIndex) HorizontalDivider()
                        }
                    }
                }
            }
        }

        if (showSoftballSection) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Softball",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        if (softballLicenses.isEmpty()) {
                            Text(
                                text = "No softball licenses available.",
                                modifier = Modifier.padding(12.dp)
                            )
                        } else {
                            softballLicenses.forEachIndexed { index, license ->
                                LicenseRow(license = license, onClick = onLicenseClick)
                                if (index < softballLicenses.lastIndex) HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}
