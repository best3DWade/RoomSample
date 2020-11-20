package com.example.roomsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomsample.WordsApplication
import com.example.roomsample.bean.Word
import com.example.roomsample.dao.WordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDataCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(wordDao = database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao){
            wordDao.deleteAll()

            var word = Word(0,"Hello")
            wordDao.insert(word)

            word = Word(1,"Todo!")
            wordDao.insert(word)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDataBase(context: Context): WordRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDataCallback(WordsApplication().applicatonScope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}