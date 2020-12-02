package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.example.cc17uielementspart2.models.Album

class AddAlbum : AppCompatActivity() {
    lateinit var albumTitleET: EditText
    lateinit var releaseDateDP: DatePicker
    lateinit var addAlbumBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_album)

        val databaseHandler = AlbumsTableHandler(this)

        albumTitleET = findViewById(R.id.editATitleET)
        releaseDateDP = findViewById(R.id.editReleaseDateDP)

        addAlbumBtn = findViewById(R.id.updateAlbumBtn)
        addAlbumBtn.setOnClickListener {
            val albumTitle = albumTitleET.text.toString()
            var releaseDate =
                "${releaseDateDP.month + 1}/${releaseDateDP.dayOfMonth}/${releaseDateDP.year}"
            releaseDateDP.init(2020, 1, 1, object : DatePicker.OnDateChangedListener {
                override fun onDateChanged(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    releaseDate =
                        "${releaseDateDP.month + 1}/${releaseDateDP.dayOfMonth}/${releaseDateDP.year}"
                }
            })

            val album = Album(album_title = albumTitle, release_date = releaseDate)

            if (databaseHandler.create(album)) {
                Toast.makeText(
                    applicationContext,
                    "Album successfully created.",
                    Toast.LENGTH_SHORT
                ).show()
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

    fun clearFields() {
        albumTitleET.text.clear()
        releaseDateDP.updateDate(2020, 1, 1)
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
            R.id.go_to_add_album-> {
                startActivity(Intent(this, AddAlbum::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}