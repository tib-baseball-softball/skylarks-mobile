package de.davidbattefeld.berlinskylarks.ui.news

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.GameReportDetail
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack

@Composable
fun NewsScreen(
    vm: NewsViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
    detailRoute: (GameReportDetail) -> Unit,
) {

}