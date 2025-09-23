package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.berlinskylarks.shared.data.model.Functionary

@Composable
fun FunctionaryRow(
    functionary: Functionary, modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
        Text(
            text = "${functionary.person.lastName}, ${functionary.person.firstName}",
        )
    }, supportingContent = {
        Text(
            text = functionary.function,
        )
    }, leadingContent = { FunctionaryIcon(functionary = functionary) }, modifier = modifier
    )
}
