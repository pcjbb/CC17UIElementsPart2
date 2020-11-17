package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView

class AlbumsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        val GridView = findViewById<GridView>(R.id.albumsGridView) as GridView

        GridView.adapter = ImageAdapter(applicationContext)

        GridView.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            val intent = Intent(this, AlbumDetailsActivity::class.java)
            var albumSongs = arrayListOf<String>()
            var uri: String = ""
            var albumTitle: String = ""
            var albumArtist: String = ""
            if (position == 0) {
                uri = "@drawable/kidkrow"
                albumTitle = getString(R.string.album1)
                albumArtist = getString(R.string.artist1)
                Log.i("album", "albumname: $albumTitle")
                albumSongs.clear()
                albumSongs.addAll(resources.getStringArray(R.array.kidKrow))
            } else if (position == 1) {
                uri = "@drawable/nswy"
                albumTitle = getString(R.string.album2)
                albumArtist = getString(R.string.artist2)
                albumSongs.clear()
                albumSongs.addAll(resources.getStringArray(R.array.noSongWithoutYou))
            } else {
                uri = "@drawable/odaat"
                albumTitle = getString(R.string.album3)
                albumArtist = getString(R.string.artist3)
                albumSongs.clear()
                albumSongs.addAll(resources.getStringArray(R.array.oneDayAtATime))
            }
            intent.putStringArrayListExtra("songs", albumSongs)
            intent.putExtra("imageUri", uri)
            intent.putExtra("albumTitle", albumTitle)
            intent.putExtra("albumArtist", albumArtist)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }
}