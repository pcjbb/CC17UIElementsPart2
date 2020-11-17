package com.example.cc17uielementspart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class QueuedSongsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        val songsQueue = intent.getStringArrayListExtra("songs")
        val songsQueueArray = songsQueue!!.toTypedArray()
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsQueueArray)
        val queuedSongsListView = findViewById<ListView>(R.id.songsQueued)
        queuedSongsListView.adapter = adapter
    }
}

