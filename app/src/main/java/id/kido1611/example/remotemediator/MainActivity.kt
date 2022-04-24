package id.kido1611.example.remotemediator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.kido1611.example.remotemediator.presenter.discover.movie.DiscoverMovieScreen
import id.kido1611.example.remotemediator.presenter.main.MainScreen
import id.kido1611.example.remotemediator.presenter.popular.movie.PopularMovieScreen
import id.kido1611.example.remotemediator.ui.theme.RemoteMediatorExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteMediatorExampleTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home") {
                    composable("home") {
                        MainScreen(
                            openMediator = {
                                navController.navigate("discover")
                            },
                            openNetworkBound = {
                                navController.navigate("popular")
                            }
                        )
                    }
                    composable("discover") {
                        DiscoverMovieScreen()
                    }
                    composable("popular") {
                        PopularMovieScreen()
                    }
                }
            }
        }
    }
}