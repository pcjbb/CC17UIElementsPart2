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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.cc17uielementspart2.models.Song
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

var songsOnQueue = ArrayList<String>()

class MainActivity : AppCompatActivity() {

    var songsArray = arrayListOf<String>()

    lateinit var adapter: ArrayAdapter<String>
    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songs: MutableList<Song>
    lateinit var songsListView: ListView
    lateinit var songsString: MutableList<String>

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get table handler
        songsTableHandler = SongsTableHandler(this)

        //get records
        songs = songsTableHandler.read()
        songsString = songsTableHandler.songsInString()

        //add values
        var oldSongs = resources.getStringArray(R.array.kidKrow) + resources.getStringArray(R.array.noSongWithoutYou) + resources.getStringArray(R.array.oneDayAtATime)
        //to alphabetize list
        oldSongs.sort()
        var allSongs = songsString + oldSongs
        for (song in allSongs) {
            songsArray.add(song)
        }

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
            R.id.edit_song -> {
                //get the song selected
                val song_id = songs[menuInfo.position].id

                //put as an extra
                val intent = Intent(this, EditSong::class.java)
                intent.putExtra("song_id", song_id)
                startActivity(intent)
                true
            }
            R.id.delete_song -> {
                val song = songs[menuInfo.position]
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val dialogBuilder = AlertDialog.Builder(this)
                var songName = songsArray[menuInfo.position]
                dialogBuilder.setMessage("Do you want to delete \"$songName\" ?")
                        .setCancelable(false)
                        //when Ok is selected
                        .setPositiveButton("OK") { _, _ ->
                            songsArray.removeAt(menuInfo.position)
                            if (songsTableHandler.delete(song)) {
                                songsArray.removeAt(menuInfo.position)
                                adapter.notifyDataSetChanged()
                                Toast.makeText(applicationContext, "Deleted $songName", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
                            }

                            //get notif service as notif manager
                            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                notificationChannel = NotificationChannel(
                                        channelId, description, NotificationManager.IMPORTANCE_HIGH)
                                notificationChannel.enableLights(true)
                                notificationChannel.lightColor = Color.CYAN
                                notificationChannel.enableVibration(true)
                                notificationManager.createNotificationChannel(notificationChannel)

                                builder = Notification.Builder(this, channelId)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted $songName.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)

                            } else {
                                builder = Notification.Builder(this)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted $songName.")
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

                true
            }
            R.id.add_to_album -> {
                val intent = Intent(applicationContext, AlbumOptions::class.java)
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                var songToAdd = (songsArray[menuInfo.position])
                intent.putExtra("addThis", songToAdd)
                startActivity(intent)
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

}
