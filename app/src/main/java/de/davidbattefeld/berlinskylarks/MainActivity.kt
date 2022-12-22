package de.davidbattefeld.berlinskylarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import ui.BottomNavGraph
import ui.NavBar

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BerlinSkylarksTheme {
                navController = rememberNavController()
                MyApp(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(navController: NavHostController) {

    Scaffold(
        bottomBar = { NavBar(navController = navController) }
    ) { padding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(padding))
    }
//    Surface(
//        modifier = modifier,
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Greeting("Android")
//    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}

/*
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BerlinSkylarksTheme {
        MyApp(navController = NavHostController())
    }
}*/
