package com.example.rafaellat.journaler.activity

import android.location.Location
import android.location.LocationListener
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.database.Db
import com.example.rafaellat.journaler.location.LocationProvider
import com.example.rafaellat.journaler.model.Note
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : ItemActivity() {
    private var note: Note? = null
    override val tag = "Note activity"
    private var location: Location? = null
    override fun getLayout() = R.layout.activity_note

    //listener assigned to EditText view and on each change the proper update method will be triggered
    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (note == null) {
                val title = getNoteTitle()
                val content = getNoteContent()
                val location = location
                note = Note("", content, location)
            }
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            Log.d(tag, p0.toString())
            updateNote()
        }
    }

    //current's user location
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            Log.i(tag, "TEST0")
            p0?.let {
                LocationProvider.unsubscribe(this)
                location = p0
                val title = getNoteTitle()
                val content = getNoteContent()
                note = Note(title, content, p0)
                val task = object : AsyncTask<Note, Void, Boolean>() {

                    override fun doInBackground(vararg params: Note?): Boolean {

                        Log.i(tag, "TEST1")
                        if (!params.isEmpty()) {
                            val param = params[0]
                            param?.let {
                                return Db.NOTE.insert(param) > 0
                            }
                        }
                        return false
                    }

                    override fun onPostExecute(result: Boolean?) {
                        result?.let {
                            if (result) {
                                Log.i(tag, "Note inserted.")
                            } else {
                                Log.e(tag, "Note not inserted.")
                            }
                        }
                    }
                }
                task.execute(note)
            }
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

        override fun onProviderEnabled(p0: String?) {}

        override fun onProviderDisabled(p0: String?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note_title.addTextChangedListener(textWatcher)
        note_content.addTextChangedListener(textWatcher)
    }

    private fun updateNote() {
        if (note == null) {
            Log.i(tag, "TEST01")
            if (!TextUtils.isEmpty(getNoteTitle()) && !TextUtils.isEmpty(getNoteContent())) {
                LocationProvider.subscribe(locationListener)
            }
        } else {
            Log.i(tag, "TEST00")
            note?.title = getNoteTitle()
            note?.message = getNoteContent()
            val task = object : AsyncTask<Note, Void, Boolean>() {
                override fun doInBackground(vararg params: Note?): Boolean {
                    if (!params.isEmpty()) {
                        val param = params[0]
                        param?.let {
                            return Db.NOTE.update(param) > 0
                        }
                    }
                    return false
                }


                override fun onPostExecute(result: Boolean?) {
                    result?.let {
                        if (result) {
                            Log.i(tag, "Note updated.")
                        } else {
                            Log.e(tag, "Note not updated.")
                        }
                    }
                }
            }
            task.execute(note)
        }
    }

    private fun getNoteContent(): String {
        return note_content.text.toString()
    }

    private fun getNoteTitle(): String {
        return note_title.text.toString()
    }

}