package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.cc17uielementspart2.models.Song
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList
val songsOnQueue = ArrayList<String>()
var songsArray = arrayListOf<String>()
class MainActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songs: MutableList<Song>
    lateinit var songsListView: ListView
    var addedSongs : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get table handler
        songsTableHandler = SongsTableHandler(this)

        //get records
        songs = songsTableHandler.read()

        //add values in ArrayList
        songsArray.addAll(resources.getStringArray(R.array.kidKrow))
        songsArray.addAll(resources.getStringArray(R.array.noSongWithoutYou))
        songsArray.addAll(resources.getStringArray(R.array.oneDayAtATime))
        for(song in songs){
            songsArray.add(song.toString())
        }

        //to alphabetize list
        Collections.sort(songsArray, String.CASE_INSENSITIVE_ORDER)

        //adapter for songs list
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        songsListView = findViewById<ListView>(R.id.songsList)
        songsListView.adapter = adapter
        registerForContextMenu(songsListView)

    }

    //inflate main menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //function when an option is picked
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
                startActivity(intent)
                true
            }
            R.id.go_to_add_song-> {
                startActivity(Intent(this, AddNewSong::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //context menu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    //function when context menu item is selected
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.add_to_queue -> {
                //add selected song to queue
                songsOnQueue.add(songsArray[menuInfo.position])

                //snackbar
                val songsListView: ListView = findViewById<ListView>(R.id.songsList)
                val snackbar: Snackbar = Snackbar.make(songsListView, "${songsArray[menuInfo.position]} has been added to QUEUE", Snackbar.LENGTH_SHORT)
                snackbar.setAction("GO TO QUEUE", View.OnClickListener {
                    startActivity(Intent(this, QueuedSongsActivity::class.java))
                })
                snackbar.show()

                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

}