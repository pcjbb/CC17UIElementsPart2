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

        //kuha ng values from albums intent
        val uri = intent.getStringExtra("imageUri")
        val title = intent.getStringExtra("albumTitle")
        val artist = intent.getStringExtra("albumArtist")
        val albumSongs = intent.getStringArrayListExtra("songs")

        //mapping ng views
        val albumTitle = findViewById<TextView>(R.id.albumTitle)
        val albumArtist = findViewById<TextView>(R.id.albumArtist)
        //change text based sa values from prev intent
        albumTitle.setText(title)
        albumArtist.setText(artist)

        //map
        val albumDetailsList = findViewById<ListView>(R.id.albumDetailsList)
        //convert to array from arrayList
        var songsArray = albumSongs!!.toTypedArray()
        //adapter for album details list
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        albumDetailsList.adapter = adapter
        //map
        val AlbumCover = findViewById<ImageView>(R.id.albumCover)
        //change image based on the val passed from prev act
        var imageResource = getResources().getIdentifier(uri, null, getPackageName())
        var res = getResources().getDrawable(imageResource)
        AlbumCover.setImageDrawable(res)
    }
}