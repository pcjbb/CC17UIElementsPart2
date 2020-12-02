package com.example.cc17uielementspart2

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.cc17uielementspart2.models.Album
import java.util.*
import kotlin.collections.ArrayList

class AlbumsActivity : AppCompatActivity() {

    lateinit var albumsTableHandler: AlbumsTableHandler
    var adapter: AlbumAdapter? = null
    var albumsArrayList = ArrayList<AlbumItem>()
    var albumNames = arrayListOf<String>()
    var images =
        intArrayOf(R.drawable.kidkrow, R.drawable.nswy, R.drawable.odaat, R.drawable.new_album)
    lateinit var albums: MutableList<Album>
    lateinit var albumsInString: MutableList<String>
    lateinit var albumGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        //get table handler
        albumsTableHandler = AlbumsTableHandler(this)

        //get records
        albums = albumsTableHandler.read()
        albumsInString = albumsTableHandler.albumsInString()
        //load albums
        var albumTitles = resources.getStringArray(R.array.albums)
        for (album in albumTitles) {
            albumNames.add(album.toString())
        }
        for (album in albumsInString) {
            albumNames.add(album)
        }
        for (album in images.indices) {
            albumsArrayList.add(AlbumItem(albumNames[album], images[album]))
        }

        for (album in albumNames.indices) {
            if (album > 3) {
                albumsArrayList.add(AlbumItem(albumNames[album], images[3]))
            }
        }

        adapter = AlbumAdapter(this, albumsArrayList)
        //map grid view
        albumGridView = findViewById(R.id.albumsGridView) //image adapter for grid view
        albumGridView.adapter = adapter
        adapter!!.notifyDataSetChanged()


        //item listener when an album cover is clicked
        albumGridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, v, position, id ->
                //intent for passing values to the next act
                val intent = Intent(this, AlbumDetailsActivity::class.java)
                intent.putExtra("position", albumNames[position])
                intent.putExtra("name", albumNames[position])
                startActivity(intent)
            }
        registerForContextMenu(albumGridView)
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
            R.id.go_to_add_song -> {
                startActivity(Intent(this, AddNewSong::class.java))
                true
            }
            R.id.go_to_add_album -> {
                startActivity(Intent(this, AddAlbum::class.java))
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
        inflater.inflate(R.menu.album_menu, menu)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.edit_album -> {
                val album_id = albums[menuInfo.position - 3].id
                val intent = Intent(this, EditAlbum::class.java)
                intent.putExtra("album_id", album_id)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}


class AlbumAdapter(private var context: Context, private var item: ArrayList<AlbumItem>) :
    BaseAdapter() {
    // var albumsArrayList = ArrayList<AlbumItem>()

    override fun getCount(): Int {
        return item.size
    }

    override fun getItem(position: Int): Any {
        return item[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val albumItem = this.item[position]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var albumView = inflator.inflate(R.layout.album_entry, null)

        var imageView = albumView?.findViewById<ImageView>(R.id.albumIV)
        var textView = albumView?.findViewById<TextView>(R.id.albumTitleTV)

        imageView?.setImageResource(albumItem.image!!)
        textView?.text = item[position].name

        return albumView!!
    }
}

private fun ImageView?.setImageResource(image: String) {

}
