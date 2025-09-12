package com.example.bookfinderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.bookfinderapp.data.local.AppDatabase
import com.example.bookfinderapp.data.repository.BookRepositoryImpl
import com.example.bookfinderapp.di.NetworkModule
import com.example.bookfinderapp.presentation.details.DetailsViewModel
import com.example.bookfinderapp.presentation.search.SearchViewModel
import com.example.bookfinderapp.ui.AppNavGraph
import com.example.bookfinderapp.ui.theme.BookFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setup DB + Repository
        val db = AppDatabase.getDatabase(applicationContext)
        val repository = BookRepositoryImpl(
            api = NetworkModule.api,
            bookDao = db.bookDao()
        )

        val searchViewModel = SearchViewModel(repository)
        val detailsViewModel = DetailsViewModel(repository)

        //Setup Compose Content
        setContent {
            BookFinderTheme {
                val navController = rememberNavController()

                AppNavGraph(
                    navController = navController,
                    searchViewModel = searchViewModel,
                    detailsViewModel = detailsViewModel
                )
            }
        }
    }
}
