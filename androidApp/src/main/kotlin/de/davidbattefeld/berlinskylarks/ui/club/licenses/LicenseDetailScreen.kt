package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.layout.Column
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
import de.davidbattefeld.berlinskylarks.ui.nav.LicenseDetail
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LicenseDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseDetailScreen(
    vm: LicenseDetailViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val license by vm.license.collectAsState()

    if (license == null) {
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = LicenseDetail.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            license?.let { l ->
                LicenseDetailCard(license = l, modifier = Modifier.padding(12.dp))
                LicenseDetailHolderCard(license = l, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
            }
        }
    }
}