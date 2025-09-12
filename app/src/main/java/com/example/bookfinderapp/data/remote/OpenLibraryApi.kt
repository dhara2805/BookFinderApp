package com.example.bookfinderapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    // Search books by title with pagination
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): SearchResponseDto

    // Get detailed info about a work
    @GET("works/{workId}.json")
    suspend fun getBookDetails(
        @Path("workId") workId: String
    ): WorkDetailsDto
}
