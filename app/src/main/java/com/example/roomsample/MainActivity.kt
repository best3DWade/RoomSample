package com.example.roomsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomsample.adapter.WordListAdapter
import com.example.roomsample.bean.Word
import com.example.roomsample.viewModels.WordViewModel
import com.example.roomsample.viewModels.WordViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }
    private val newWordActivityRequestCode = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wordAdapter = WordListAdapter()
        recyclerview.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        wordViewModel.allWords.observe(this, Observer {
            words -> words?.let { wordAdapter.submitList(it) }
        })

        fab.setOnClickListener {
            val intent = Intent(this,NewWordActivity::class.java)
            startActivityForResult(intent,newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra("extra")?.let {
                val word = Word(2,it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}