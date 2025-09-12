package com.example.bookfinderapp.utils

import com.example.bookfinderapp.data.remote.WorkDetailsDto

import com.example.bookfinderapp.domain.model.BookDetails

fun WorkDetailsDto.toDomain(workId: String): BookDetails {
    val coverUrl = covers?.firstOrNull()?.let { "https://covers.openlibrary.org/b/id/${it}-L.jpg" }

    val descriptionText = when (description) {
        is String -> description as String
        is Map<*, *> -> (description["value"] as? String) ?: ""
        else -> ""
    }

    val authorName = authors
        ?.firstOrNull()
        ?.author
        ?.key

    val publishYear = firstPublishDate?.take(4)?.toIntOrNull()

    return BookDetails(
        id = workId,
        title = title ?: "Untitled",
        author = authorName ?: "Unknown",
        coverUrl = coverUrl,
        description = descriptionText,
        publishYear = publishYear)
}
