package com.example.cc17uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.cc17uielementspart2.models.Album

var albumsList : ArrayList<String> = ArrayList()
class AlbumOptions : AppCompatActivity() {
    lateinit var albumsTableHandler: AlbumsTableHandler
    lateinit var albumsListView: ListView
    lateinit var albums: MutableList<Album>
    lateinit var albumsInString: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_options)

        albumsTableHandler = AlbumsTableHandler(this)
        albums = albumsTableHandler.read()
        albumsInString = albumsTableHandler.albumsInString()

        albumsListView = findViewById(R.id.albumsListView)
        for (album in albumsInString) {
            albumsList.add(album)
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albumsList)
        albumsListView.adapter = adapter
        adapter.notifyDataSetChanged()

        val addSong: String = intent.extras!!.getString("addThis").toString()
        albumsListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
                val intent = Intent(this, AlbumDetailsActivity::class.java)
                intent.putExtra("addThis", addSong)
                intent.putExtra("album_title", albumsList[position])
                startActivity(intent)
            }
    }
}