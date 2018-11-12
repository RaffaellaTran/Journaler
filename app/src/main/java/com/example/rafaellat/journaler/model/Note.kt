package com.example.rafaellat.journaler.model

import android.location.Location
import android.os.Parcel
import android.os.Parcelable

class Note(
    title: String,
    message: String,
    location: Location?= null
) : Entry(title, message, location), Parcelable {

    override var id = 0L // new instantiated note

    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Location::class.java.classLoader)
    ){
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(message)
        parcel.writeParcelable(location, 0)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<Note>{
        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }

        override fun createFromParcel(source: Parcel): Note {
            return Note(source)
        }
    }
}