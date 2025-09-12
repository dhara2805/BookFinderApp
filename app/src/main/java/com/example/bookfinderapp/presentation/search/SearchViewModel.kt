package com.example.bookfinderapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.domain.model.Book
import com.example.bookfinderapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 1,
    val isEndReached: Boolean = false
)

class SearchViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private var currentQuery: String = ""

    fun searchBooks(query: String) {
        if (query != currentQuery) {
            _uiState.value = SearchUiState(isLoading = true)
            currentQuery = query
        }
        fetchBooks(query, _uiState.value.page)
    }

    fun loadNextPage() {
        val state = _uiState.value
        if (!state.isLoading && !state.isEndReached) {
            fetchBooks(currentQuery, state.page + 1)
        }
    }

    private fun fetchBooks(query: String, page: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val books = repository.searchBooks(query, page)
                val allBooks = if (page == 1) books else _uiState.value.books + books
                _uiState.value = _uiState.value.copy(
                    books = allBooks,
                    isLoading = false,
                    page = page,
                    isEndReached = books.isEmpty()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
