package com.example.bookfinderapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookfinderapp.domain.model.BookDetails

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String?,
    val coverUrl: String?,
    val publishYear: Int?,
    val description: String?,
    val isSaved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

fun BookEntity.toDomainDetails(): BookDetails {
    return BookDetails(
        id = this.id,
        title = this.title,
        author = this.author ?: "Unknown",
        coverUrl = this.coverUrl,
        description = this.description ?: "",
        publishYear = this.publishYear
    )
}

