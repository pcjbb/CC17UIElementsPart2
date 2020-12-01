package com.example.cc17uielementspart2

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*

class AlbumDetailsActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    var songsArray = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        //get values from albums intent
        val uri = intent.getStringExtra("imageUri")
        val title = intent.getStringExtra("albumTitle")
        val artist = intent.getStringExtra("albumArtist")
        var albumSongs = intent.getStringArrayListExtra("songs")

        //change image based on the val passed from prev act
        var imageResource = getResources().getIdentifier(uri, null, getPackageName())
        var res = getResources().getDrawable(imageResource)
        findViewById<ImageView>(R.id.albumCover).setImageDrawable(res)

        //mapping of text views and assigning text based on passed values
        findViewById<TextView>(R.id.albumTitle).setText(title)
        findViewById<TextView>(R.id.albumArtist).setText(artist)

        //map
        val albumDetailsList = findViewById<ListView>(R.id.albumDetailsList)
        //convert to array from arrayList
        songsArray = albumSongs!!

        //adapter for album details list
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        albumDetailsList.adapter = adapter
        registerForContextMenu(albumDetailsList)

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_song -> {
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val dialogBuilder = AlertDialog.Builder(this)
                var songName = songsArray[menuInfo.position]
                dialogBuilder.setMessage("Do you want to delete \"$songName\" ?")
                        .setCancelable(false)
                        //when Ok is selected
                        .setPositiveButton("OK") { _, _ ->
                            //toast when a song is removed
                            val toast: Toast = Toast.makeText(this, "Deleted $songName", Toast.LENGTH_SHORT)
                            toast.show()

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
                                        .setContentText("You deleted $songName from the album.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)

                            } else {
                                builder = Notification.Builder(this)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted $songName from the album.")
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
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                songsArray.removeAt(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}