package com.example.bookfinderapp.di

import com.example.bookfinderapp.data.remote.OpenLibraryApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val BASE_URL = "https://openlibrary.org/"

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OpenLibraryApi by lazy {
        retrofit.create(OpenLibraryApi::class.java)
    }
}
