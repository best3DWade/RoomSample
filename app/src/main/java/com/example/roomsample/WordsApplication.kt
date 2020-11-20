package com.example.roomsample

import android.app.Application
import com.example.roomsample.database.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication :Application() {

    val applicatonScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDataBase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}