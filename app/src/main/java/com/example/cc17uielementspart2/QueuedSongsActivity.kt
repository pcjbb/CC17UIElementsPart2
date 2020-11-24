package com.example.cc17uielementspart2

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class QueuedSongsActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queued_songs)

        //adapter for queued songs list
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsOnQueue)
        val queuedSongsListView = findViewById<ListView>(R.id.songsQueued)
        queuedSongsListView.adapter = adapter
        registerForContextMenu(queuedSongsListView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_song -> {
                //toast
                val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val toast: Toast = Toast.makeText(this, "$songsOnQueue has been removed from QUEUE", Toast.LENGTH_LONG)

                //removes song from queue
                songsOnQueue.removeAt(menuInfo.position)
                adapter.notifyDataSetChanged()
                toast.show()

                //get notif service as notif manager
                notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                //if the queue is empty
                if (adapter.isEmpty) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel = NotificationChannel(
                                channelId, description, NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.CYAN
                        notificationChannel.enableVibration(true)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(this, channelId)
                                .setContentTitle("Queue Emptied")
                                .setContentText("There are no songs on queue.")
                                .setSmallIcon(R.drawable.ic_launcher_background)

                    } else {
                        builder = Notification.Builder(this)
                                .setContentTitle("Queue Emptied")
                                .setContentText("There are no songs on queue.")
                                .setSmallIcon(R.drawable.ic_launcher_background)

                    }
                    notificationManager.notify(1234, builder.build())
                }

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}

