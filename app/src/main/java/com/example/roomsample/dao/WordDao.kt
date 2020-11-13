package com.example.roomsample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomsample.bean.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("select * from word_table order by word ASC")
    fun getAlphabetizedWords() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("delete from word_table")
    suspend fun deleteAll()
}