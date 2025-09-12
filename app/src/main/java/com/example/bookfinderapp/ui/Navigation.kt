package com.example.bookfinderapp.ui

import androidx.compose.runtime.Composable
import com.example.bookfinderapp.presentation.details.DetailsViewModel
import com.example.bookfinderapp.presentation.search.SearchViewModel
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookfinderapp.presentation.details.DetailsScreen
import com.example.bookfinderapp.presentation.search.SearchScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    detailsViewModel: DetailsViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "search",
        modifier = modifier
    ) {
        composable("search") {
            SearchScreen(
                viewModel = searchViewModel,
                navController = navController
            )
        }

        composable("details/{workId}") { backStackEntry ->
            val workId = backStackEntry.arguments?.getString("workId") ?: return@composable
            DetailsScreen(
                workId = workId,
                viewModel = detailsViewModel,
                navController = navController
            )
        }
    }
}

