package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.cc17uielementspart2.models.Song
import com.google.android.material.snackbar.Snackbar

class EditSong : AppCompatActivity() {
    lateinit var editTitleET: EditText
    lateinit var editArtistET: EditText
    lateinit var editAlbumET: EditText
    lateinit var updateSongBtn: Button
    lateinit var song : Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)

        val song_id = intent.getIntExtra("song_id", 0)

        //get song from database
        val databaseHandler = SongsTableHandler(this)
        song = databaseHandler.readOne(song_id)

        editTitleET = findViewById(R.id.editTitleET)
        editArtistET = findViewById(R.id.editArtistET)
        editAlbumET = findViewById(R.id.editAlbumET)
        updateSongBtn = findViewById(R.id.updateSongBtn)

        editTitleET.setText(song.title)
        editArtistET.setText(song.artist)
        editAlbumET.setText(song.album)

        updateSongBtn.setOnClickListener {
            //get fields
            val title = editTitleET.text.toString()
            val artist = editArtistET.text.toString()
            val album = editAlbumET.text.toString()

            //assign to a  model
            val updated_song = Song(id = song.id, title = title, artist = artist, album = album)

            //save to the database
            if (databaseHandler.update(updated_song)) {
                Toast.makeText(applicationContext, "Song has been updated.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Oops! Something went wrong.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
}