package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.lang.String.CASE_INSENSITIVE_ORDER
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val songsOnQueue = ArrayList<String>()
    var songsArray = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //para alphabetized
        songsArray.addAll(resources.getStringArray(R.array.kidKrow))
        songsArray.addAll(resources.getStringArray(R.array.noSongWithoutYou))
        songsArray.addAll(resources.getStringArray(R.array.oneDayAtATime))
        Collections.sort(songsArray, String.CASE_INSENSITIVE_ORDER)

        //adapter for songs list
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songsListView = findViewById<ListView>(R.id.songsList)
        songsListView.adapter = adapter
        registerForContextMenu(songsListView)

    }

    //inflate main menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    //what will hapen when option is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_songs -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.go_to_albums -> {
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }
            R.id.go_to_queue -> {
                val intent = Intent(this, QueuedSongsActivity::class.java)
                intent.putStringArrayListExtra("songs", songsOnQueue)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //context menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }
    //what will happen if menu is clicked
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.add_to_queue -> {
                songsOnQueue.add(songsArray[menuInfo.position])
                //to check lang if may laman kanina hehe
                Log.i("array", "Songs in queue: $songsOnQueue")
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

}