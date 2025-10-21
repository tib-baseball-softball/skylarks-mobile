package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.berlinskylarks.shared.data.model.License

@Composable
fun LicenseRow(
    license: License,
    onClick: (License) -> Unit,
    modifier: Modifier = Modifier
) {
    val name = "${license.person.lastName}, ${license.person.firstName}"
    ListItem(
        leadingContent = { LicenseLevelIndicator(level = license.level) },
        headlineContent = { Text(text = name) },
        modifier = modifier.clickable { onClick(license) }
    )
}
