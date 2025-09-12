package com.example.bookfinderapp.data.remote

import com.google.gson.annotations.SerializedName

data class WorkDetailsDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: Any?, // Can be String or { value: String } object
    @SerializedName("covers")
    val covers: List<Int>?,
    @SerializedName("authors")
    val authors: List<AuthorDto>?,
    @SerializedName("first_publish_date")
    val firstPublishDate: String?
)

data class AuthorDto(
    @SerializedName("author")
    val author: AuthorInfoDto
)

data class AuthorInfoDto(
    @SerializedName("key")
    val key: String
)

