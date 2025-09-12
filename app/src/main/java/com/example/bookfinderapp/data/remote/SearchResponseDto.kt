package com.example.bookfinderapp.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("docs")
    val docs: List<BookDocDto>,
    @SerializedName("numFound")
    val numFound: Int
)

data class BookDocDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("author_name")
    val authorName: List<String>?,
    @SerializedName("cover_i")
    val coverId: Int?,
    @SerializedName("key")
    val key: String,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,
    @SerializedName("isbn")
    val isbn: List<String>?
)
