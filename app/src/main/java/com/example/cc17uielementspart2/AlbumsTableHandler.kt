package com.example.cc17uielementspart2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cc17uielementspart2.models.Album

class AlbumsTableHandler(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "albums_database"
        private val TABLE_NAME = "albums"
        private val COL_ID = "id"
        private val COL_ALBUM_TITLE = "albums_title"
        private val COL_RELEASE_DATE = "release_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //TODO("Not yet implemented")
        //define query
        val query =
            "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_ALBUM_TITLE + " TEXT, " + COL_RELEASE_DATE + " TEXT)"

        //execute query
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }

    fun create(album: Album): Boolean {
        //get the database
        val database = this.writableDatabase

        //set ContentValues
        val contentValues = ContentValues()
        contentValues.put(COL_ALBUM_TITLE, album.album_title)
        contentValues.put(COL_RELEASE_DATE, album.release_date)

        //insert
        val result = database.insert(TABLE_NAME, null, contentValues)

        //check for the result
        if (result == (0).toLong()) {
            return false
        }
        return true
    }

    fun update(album: Album): Boolean {
        //get
        val database = this.writableDatabase

        //set
        val contentValues = ContentValues()
        contentValues.put(COL_ALBUM_TITLE, album.album_title)
        contentValues.put(COL_RELEASE_DATE, album.release_date)

        //update
        val result = database.update(TABLE_NAME, contentValues, "id=" + album.id, null)

        //check
        if (result == 0) {
            return false
        }
        return true

    }


    fun read(): MutableList<Album> {
        val albumsList: MutableList<Album> = ArrayList<Album>()
        val query = "SELECT * FROM " + TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLException) {
            database.execSQL(query)
            return albumsList
        }

        var id: Int
        var album_title: String
        var release_date: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
                release_date = cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE))

                val album = Album(id, album_title, release_date)
                albumsList.add(album)
            } while (cursor.moveToNext())
        }
        return albumsList
    }

    fun readOne(album_id: Int): Album {
        var album = Album(0, "", "")
        val query = "SELECT * FROM $TABLE_NAME WHERE id=$album_id"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLException) {
            database.execSQL(query)
            return album
        }
        var id: Int
        var album_title: String
        var release_date: String
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
            release_date = cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE))
            album = Album(id, album_title, release_date)
        }
        return album
    }

    fun albumsInString(): MutableList<String> {
        val albumsList: MutableList<String> = ArrayList<String>()
        val query = "SELECT * FROM " + TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLException) {
            database.execSQL(query)
            return albumsList
        }
        var album_title: String
        if (cursor.moveToFirst()) {
            do {
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
                albumsList.add(album_title)
            } while (cursor.moveToNext())
        }
        return albumsList
    }


}