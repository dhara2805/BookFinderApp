package com.example.bookfinderapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinderapp.domain.model.Book
import com.example.bookfinderapp.domain.model.BookDetails
import com.example.bookfinderapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailsViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _bookDetails = MutableStateFlow<BookDetails?>(null)
    val bookDetails: StateFlow<BookDetails?> = _bookDetails

    fun loadBookDetails(workId: String) {
        viewModelScope.launch {
            try {
                val details = repository.getBookDetails(workId)
                _bookDetails.value = details
            } catch (e: Exception) {
                e.printStackTrace()
                _bookDetails.value = null
            }
        }
    }
}

