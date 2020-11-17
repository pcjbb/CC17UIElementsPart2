package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    val songsOnQueue = ArrayList<String>()
    val songsArray = arrayOf(
        "Comfort Crowd", "Wish You Were Sober", "Maniac", "(Online Love)", "Checkmate", "The Cut That Always Bleeds", "Fight or Flight", "Affluenza", "(Can We Be Friends?)", "Heather",
        "dear p", "no song without you", "free love", "iloveyoumorethanicansay", "bymyside", "la la la that's how it goes", "one way to tokyo", "can't bear to be without you", "loving you is so easy", "s o c i a l d i s t a n c i n g",
        "Wherever You Are", "Sometimes", "Saving Grace", "Say Something", "The Evening", "Spend It With You", "Care", "Heart Open", "Everyone Changes", "In the End"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray.sorted())
        val songsListView = findViewById<ListView>(R.id.songsList)
        songsListView.adapter = adapter
        registerForContextMenu(songsListView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.add_to_queue -> {
                songsOnQueue.add(songsArray[menuInfo.position])
                //to check if may laman
                Log.i("array", "Songs in queue: $songsOnQueue")
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

}