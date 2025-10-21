package de.davidbattefeld.berlinskylarks.ui.club.field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.Field
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FieldsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldsScreen(
    vm: FieldsViewModel,
    detailRoute: (Int) -> Unit,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val fields by vm.fields.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = Field.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) }
    ) { paddingValues ->
        FieldGrid(
            fields = fields,
            onFieldClick = { detailRoute(it.id) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}