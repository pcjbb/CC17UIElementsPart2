package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cc17uielementspart2.models.Song
import com.google.android.material.snackbar.Snackbar

class AddNewSong : AppCompatActivity() {
    lateinit var songTitleET : EditText
    lateinit var songArtistET : EditText
    lateinit var songAlbumET : EditText
    lateinit var addSongBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_song)

        val databaseHandler = SongsTableHandler(this)

        songTitleET = findViewById(R.id.songTitleET)
        songArtistET = findViewById(R.id.songArtistET)
        songAlbumET = findViewById(R.id.songAlbumET)
        addSongBtn = findViewById(R.id.addSongBtn)

        addSongBtn.setOnClickListener {
            //get fields
            val title = songTitleET.text.toString()
            val artist = songArtistET.text.toString()
            val album = songAlbumET.text.toString()

            //assign to a  model
            val song = Song(title = title, artist = artist, album = album)

            //save to the database
            if (databaseHandler.create(song)) {
                val view = findViewById<View>(R.id.viewView)
                val snackbar: Snackbar =
                    Snackbar.make(view, "$title has been added", Snackbar.LENGTH_SHORT)
                snackbar.setAction("GO TO SONGS", View.OnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                })
                snackbar.show()
                Toast.makeText(applicationContext, "$title song has been added", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Oops! Something went wrong.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            clearFields()
        }
    }

        fun clearFields(){
            songTitleET.text.clear()
            songArtistET.text.clear()
            songAlbumET.text.clear()
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
            else -> super.onOptionsItemSelected(item)
        }
    }


    }
