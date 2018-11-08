package com.example.rafaellat.journaler.model

import android.location.Location
import com.example.rafaellat.journaler.database.DbModel

abstract class Entry(
    var title: String,
    var message: String,
    var location: Location?
) : DbModel()