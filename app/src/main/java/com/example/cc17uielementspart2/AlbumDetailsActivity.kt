package com.example.cc17uielementspart2

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cc17uielementspart2.models.Album
import java.util.*
import kotlin.collections.ArrayList

var albumSongs = arrayListOf<String>()

class AlbumDetailsActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    lateinit var album: Album

    var imageUri =
        arrayOf("@drawable/kidkrow", "@drawable/nswy", "@drawable/odaat", "@drawable/new_album")
    var songsArray = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        //get values from albums intent
        val position = intent.extras!!.getString("position")

        var uri: String = ""
        var albumTitle: String = ""

        if (position == "Kid Krow") {
            uri = imageUri[0]
            albumTitle = position
            albumSongs = arrayListOf(*resources.getStringArray(R.array.kidKrow))
        } else if (position == "No Song Without You") {
            uri = imageUri[1]
            albumTitle = position
            albumSongs = arrayListOf(*resources.getStringArray(R.array.noSongWithoutYou))
        } else if (position == "One Day at a Time") {
            uri = imageUri[2]
            albumTitle = position
            albumSongs = arrayListOf(*resources.getStringArray(R.array.oneDayAtATime))
        } else {
            //mali lol
            uri = imageUri[3]
            val addThisToAlbum = intent.extras!!.getString("addThis").toString()
            albumTitle = intent.extras!!.getString("album_title").toString()
            albumSongs.add(addThisToAlbum)
            Toast.makeText(this, "Added $albumSongs in $albumTitle ", Toast.LENGTH_SHORT).show()
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albumSongs)
        }

        //mapping of text views and assigning text based on passed values
        findViewById<TextView>(R.id.albumTitle).setText(albumTitle)

        //change image based on the val passed from prev act
        var imageResource = getResources().getIdentifier(uri, null, getPackageName())
        var res = getResources().getDrawable(imageResource)
        findViewById<ImageView>(R.id.albumCover).setImageDrawable(res)

        //map
        val albumDetailsList = findViewById<ListView>(R.id.albumDetailsList)


        //adapter for album details list
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumSongs)
        albumDetailsList.adapter = adapter
        registerForContextMenu(albumDetailsList)

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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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
            R.id.go_to_add_album-> {
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
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_song -> {
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Do you want to delete \"${albumSongs[menuInfo.position]}\" ?")
                    .setCancelable(false)
                    //when Ok is selected
                    .setPositiveButton("OK") { _, _ ->
                        //toast when a song is removed
                        val toast: Toast =
                            Toast.makeText(this, "Deleted song from album.", Toast.LENGTH_SHORT)
                        toast.show()

                        //get notif service as notif manager
                        notificationManager =
                            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            notificationChannel = NotificationChannel(
                                channelId, description, NotificationManager.IMPORTANCE_HIGH
                            )
                            notificationChannel.enableLights(true)
                            notificationChannel.lightColor = Color.CYAN
                            notificationChannel.enableVibration(true)
                            notificationManager.createNotificationChannel(notificationChannel)

                            builder = Notification.Builder(this, channelId)
                                .setContentTitle("Song Removed")
                                .setContentText("Successfully deleted song from the album.")
                                .setSmallIcon(R.drawable.ic_launcher_background)

                        } else {
                            builder = Notification.Builder(this)
                                .setContentTitle("Song Removed")
                                .setContentText("Successfully deleted song from the album.")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                        }
                        //calls manager
                        notificationManager.notify(0, builder.build())
                        //updates list
                        adapter.notifyDataSetChanged()
                        //when cancel is pressed
                    }.setNegativeButton("CANCEL") { dialog, _ ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.show()
                albumSongs.removeAt(menuInfo.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}