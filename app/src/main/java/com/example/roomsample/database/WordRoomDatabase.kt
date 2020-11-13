package com.example.roomsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomsample.bean.Word
import com.example.roomsample.dao.WordDao

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao() : WordDao

    companion object{
        @Volatile
        private var INSTANCE : WordRoomDatabase ?= null

        fun getDataBase(context: Context): WordRoomDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                "word_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}