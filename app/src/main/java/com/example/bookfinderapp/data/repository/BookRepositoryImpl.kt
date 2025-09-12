package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.local.BookDao
import com.example.bookfinderapp.data.local.toDomainDetails
import com.example.bookfinderapp.data.remote.OpenLibraryApi
import com.example.bookfinderapp.domain.model.Book
import com.example.bookfinderapp.domain.model.BookDetails
import com.example.bookfinderapp.domain.model.toEntity
import com.example.bookfinderapp.domain.repository.BookRepository
import com.example.bookfinderapp.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepositoryImpl(
    private val api: OpenLibraryApi, private val bookDao: BookDao
) : BookRepository {


    override suspend fun searchBooks(query: String, page: Int): List<Book> =
        withContext(Dispatchers.IO) {
            val response = api.searchBooks(query, page)
            val books = response.docs.map { doc ->
                Book(
                    id = doc.key.removePrefix("/works/"),
                    title = doc.title,
                    author = doc.authorName?.joinToString(", ") ?: "Unknown",
                    coverUrl = doc.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                    publishYear = doc.firstPublishYear,
                    description = "",
                    isSaved = bookDao.getBookById(doc.key.removePrefix("/works/")) != null
                )
            }
            books
        }

    override suspend fun getBookDetails(workId: String): BookDetails =
        withContext(Dispatchers.IO) {
            val normalizedId = workId.removePrefix("/works/")
            val local = bookDao.getBookById(normalizedId)

            return@withContext try {
                val dto = api.getBookDetails(normalizedId)
                val domain = dto.toDomain(normalizedId)

               // Update or insert into DB
                if (local != null) {
                    bookDao.insert(
                        local.copy(
                            title = domain.title,
                            author = domain.author,
                            coverUrl = domain.coverUrl,
                            publishYear = domain.publishYear,
                            description = domain.description
                        )
                    )
                } else {
                    bookDao.insert(domain.toEntity())
                }

                domain
            } catch (e: Exception) {
                // If API fails or no internet, return local copy if available
                local?.toDomainDetails() ?: throw e
            }
        }

}
