package com.example.bookfinderapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.bookfinderapp.domain.model.Book
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { viewModel.searchBooks(query.text) }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Finder") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            // Search bar
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.searchBooks(it.text)
                },
                label = { Text("Search Books") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    itemsIndexed(uiState.books) { index, book ->
                        BookItem(book) {
                            val workId = book.id.substringAfterLast("/")
                            navController.navigate("details/$workId")
                        }

                        // Pagination trigger
                        if (index == uiState.books.lastIndex && !uiState.isLoading && !uiState.isEndReached) {
                            viewModel.loadNextPage()
                        }
                    }

                    // Loading shimmer at bottom if already some items
                    if (uiState.isLoading && uiState.books.isNotEmpty()) {
                        item {
                            repeat(3) { ShimmerBookItem() }
                        }
                    }
                }

                // Pull-to-refresh indicator
                PullRefreshIndicator(
                    refreshing = uiState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            // Error message
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = book.coverUrl,
                contentDescription = book.title,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, style = MaterialTheme.typography.subtitle1)
                Text(text = book.author, style = MaterialTheme.typography.body2)
                Text(
                    text = book.publishYear?.toString() ?: "",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
fun ShimmerBookItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .placeholder(visible = true)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.height(20.dp).fillMaxWidth().placeholder(true))
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(15.dp).fillMaxWidth(0.7f).placeholder(true))
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(15.dp).fillMaxWidth(0.5f).placeholder(true))
            }
        }
    }
}
