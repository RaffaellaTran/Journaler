package com.example.rafaellat.journaler.activity

import com.example.rafaellat.journaler.R

class NoteActivity: ItemActivity() {
    override val tag: String
        get() = "NoteActivity"

    override fun getLayout() = R.layout.activity_note

}