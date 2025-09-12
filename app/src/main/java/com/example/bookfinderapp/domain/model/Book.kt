package com.example.bookfinderapp.domain.model

import com.example.bookfinderapp.data.local.BookEntity


data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String?,
    val publishYear: Int?,
    val description: String,
    val isSaved: Boolean = false
)

data class BookDetails(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String?,
    val description: String,
    val publishYear: Int? = null
)

fun BookEntity.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author.toString(),
        coverUrl = this.coverUrl,
        publishYear = this.publishYear,
        description = this.description.toString(),
        isSaved = this.isSaved
    )
}
fun BookDetails.toEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        coverUrl = this.coverUrl,
        publishYear = this.publishYear,
        description = this.description,
        isSaved = false
    )
}