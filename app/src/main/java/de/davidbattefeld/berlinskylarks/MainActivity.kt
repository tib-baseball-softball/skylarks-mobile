package de.davidbattefeld.berlinskylarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import ui.nav.NavGraph
import ui.nav.NavItemCollection
import ui.nav.NavigationType
import ui.nav.SkylarksNavDestination
import ui.nav.SkylarksTopAppBar

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BerlinSkylarksTheme {
                val windowSize = calculateWindowSizeClass(this)
                navController = rememberNavController()

                BerlinSkylarksApp(
                    navController = navController,
                    windowSize = windowSize.widthSizeClass,
                )
            }
        }
    }
}
