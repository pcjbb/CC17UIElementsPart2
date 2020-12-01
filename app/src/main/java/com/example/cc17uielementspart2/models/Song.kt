package com.example.cc17uielementspart2.models

class Song (var id: Int = 0, var title : String, var artist : String, var album : String) {
    override fun toString(): String {
        return "${title}"
        //Title: ${title}
        // Artist: ${artist}
        // Album: ${album}
    }
}