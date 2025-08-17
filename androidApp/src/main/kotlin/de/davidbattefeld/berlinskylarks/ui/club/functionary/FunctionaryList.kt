package de.davidbattefeld.berlinskylarks.ui.club.functionary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Functionary

@Composable
fun FunctionariesList(
    functionaries: List<Functionary>,
    onFunctionaryClick: (Functionary) -> Unit,
    modifier: Modifier = Modifier
) {
    if (functionaries.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No functionaries available.", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }


    LazyColumn(
        modifier = Modifier.padding(12.dp)
    ) {
        itemsIndexed(functionaries) { index, functionary ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFunctionaryClick(functionary) }
                    .padding(vertical = 2.dp)
            ) {
                FunctionaryRow(functionary = functionary)
            }

            if (index < functionaries.size - 1) {
                HorizontalDivider()
            }
        }
    }
}