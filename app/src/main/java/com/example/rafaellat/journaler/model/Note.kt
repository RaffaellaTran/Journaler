package com.example.rafaellat.journaler.model

import android.location.Location

class Note(
    title: String,
    message: String,
    location: Location?= null
) : Entry(title, message, location) {
    override var id = 0L // new instantiated note
}