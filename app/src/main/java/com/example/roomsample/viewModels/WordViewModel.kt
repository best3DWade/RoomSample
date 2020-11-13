package com.example.roomsample.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomsample.WordRepository
import com.example.roomsample.bean.Word
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository): ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}
