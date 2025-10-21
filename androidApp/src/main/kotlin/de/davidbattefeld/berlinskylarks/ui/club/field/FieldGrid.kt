package de.davidbattefeld.berlinskylarks.ui.club.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Field
import de.davidbattefeld.berlinskylarks.global.cardGridSpacing

@Composable
fun FieldGrid(
    fields: List<Field>,
    onFieldClick: (Field) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(cardGridSpacing),
        horizontalArrangement = Arrangement.spacedBy(cardGridSpacing)
    ) {
        items(fields) { field ->
            FieldCard(field = field, onClick = onFieldClick)
        }
    }
}
