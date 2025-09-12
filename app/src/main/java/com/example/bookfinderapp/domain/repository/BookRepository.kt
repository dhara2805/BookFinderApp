package com.example.bookfinderapp.domain.repository

import com.example.bookfinderapp.domain.model.Book
import com.example.bookfinderapp.domain.model.BookDetails
import retrofit2.http.Path

interface BookRepository {
    suspend fun searchBooks(query: String, page: Int): List<Book>
    suspend fun getBookDetails(@Path("workId") workId: String): BookDetails
}
