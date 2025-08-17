package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet // Euro
import androidx.compose.material.icons.filled.Create // Pen
import androidx.compose.material.icons.filled.Edit // Pencil
import androidx.compose.material.icons.filled.EmojiEvents // Crown
import androidx.compose.material.icons.filled.Group // Users
import androidx.compose.material.icons.filled.Person // User
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import de.berlinskylarks.shared.data.model.Functionary

@Composable
fun FunctionaryIcon(
    functionary: Functionary,
    modifier: Modifier = Modifier
) {
    val iconVector: ImageVector = when {
        functionary.function.contains(
            "Abteilungsleit",
            ignoreCase = true
        ) -> Icons.Filled.EmojiEvents

        functionary.category.contains(
            "Kasse",
            ignoreCase = true
        ) -> Icons.Filled.AccountBalanceWallet

        functionary.function.contains("Schriftwart", ignoreCase = true) -> Icons.Filled.Create
        functionary.category.contains("Jugend", ignoreCase = true) -> Icons.Filled.Group
        functionary.category.contains("Umpire", ignoreCase = true) -> Icons.Filled.Person
        functionary.category.contains("Scorer", ignoreCase = true) -> Icons.Filled.Edit
        else -> Icons.Filled.Person
    }

    val iconColor =
        if (functionary.category == "Vorstand/Abteilungsleitung" || functionary.category == "Kasse/Finanzen") {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary // Or tertiary, adjust as per your theme
        }

    Icon(
        imageVector = iconVector,
        contentDescription = null,
        tint = iconColor,
        modifier = modifier
    )
}
