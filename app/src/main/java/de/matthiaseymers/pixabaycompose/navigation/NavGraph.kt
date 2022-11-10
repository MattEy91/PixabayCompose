package de.matthiaseymers.pixabaycompose.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.matthiaseymers.pixabaycompose.R
import de.matthiaseymers.pixabaycompose.core.Constants
import de.matthiaseymers.pixabaycompose.core.Constants.EXTRA_ARGUMENT_HIT
import de.matthiaseymers.pixabaycompose.core.showGenericErrorToast
import de.matthiaseymers.pixabaycompose.domain.model.Hit
import de.matthiaseymers.pixabaycompose.ui.detail.DetailScreen
import de.matthiaseymers.pixabaycompose.ui.search.SearchScreen

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Constants.DESTINATION_SEARCH
    ) {
        composable(Destination.Search.route) { SearchScreen(navHostController = navController) }
        composable(Destination.Detail.route) {
            val hit = navController.previousBackStackEntry?.savedStateHandle?.get<Hit>(EXTRA_ARGUMENT_HIT)
            hit?.let {
                DetailScreen(navHostController = navController, it)
            } ?: run {
                LocalContext.current.showGenericErrorToast()
            }
        }
    }
}