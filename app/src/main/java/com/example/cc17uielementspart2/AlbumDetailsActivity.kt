package com.example.cc17uielementspart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val uri = intent.getStringExtra("imageUri")
        val title = intent.getStringExtra("albumTitle")
        val artist = intent.getStringExtra("albumArtist")
        val albumSongs = intent.getStringArrayListExtra("songs")

        var songsArray = albumSongs!!.toTypedArray()
        val albumDetailsList = findViewById<ListView>(R.id.albumDetailsList)
        val AlbumCover = findViewById<ImageView>(R.id.albumCover)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        albumDetailsList.adapter = adapter
        var imageResource = getResources().getIdentifier(uri, null, getPackageName())
        var res = getResources().getDrawable(imageResource)
        AlbumCover.setImageDrawable(res)

        val albumTitle = findViewById<TextView>(R.id.albumTitle)
        val albumArtist = findViewById<TextView>(R.id.albumArtist)
        albumTitle.setText(title)
        albumArtist.setText(artist)


    }
}