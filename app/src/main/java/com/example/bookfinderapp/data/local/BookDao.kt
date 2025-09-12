package com.example.bookfinderapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY createdAt DESC")
    suspend fun getAll(): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getBookById(id: String): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' ORDER BY title ASC")
    suspend fun searchBooksByTitle(query: String): List<BookEntity>

}
