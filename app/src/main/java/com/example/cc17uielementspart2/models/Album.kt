package com.example.cc17uielementspart2.models

class Album(var id: Int = 0, var album_title: String, var release_date: String) {
    override fun toString(): String {
        return "${album_title}"
    }
}
