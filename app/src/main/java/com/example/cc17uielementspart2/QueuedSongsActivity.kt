package com.example.cc17uielementspart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class QueuedSongsActivity : AppCompatActivity() {

    lateinit var adapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        //adapter for queued songs list
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsOnQueue)
        val queuedSongsListView = findViewById<ListView>(R.id.songsQueued)
        queuedSongsListView.adapter = adapter
        registerForContextMenu(queuedSongsListView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_song -> {
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val toast: Toast = Toast.makeText(this, "$songsOnQueue has been removed from QUEUE", Toast.LENGTH_SHORT)
                songsOnQueue.removeAt(menuInfo.position)
                adapter.notifyDataSetChanged()
                toast.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}

