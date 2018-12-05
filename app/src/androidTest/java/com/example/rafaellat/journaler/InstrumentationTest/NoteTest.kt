package com.example.rafaellat.journaler.InstrumentationTest

import android.location.Location
import com.example.rafaellat.journaler.database.Content
import com.example.rafaellat.journaler.model.Note
import org.junit.Test
import kotlin.test.assertEquals

class NoteTest {

    var note = Note(
        "title ", "message", Location("Stub")
    )

    @Test
    fun noteTestInsert() {

        note.id = Content.NOTE.insert(note)
        assertEquals(254, note.id)
    }

    @Test
    fun noteTestDelete() {
        noteTestInsert()
        val count = Content.NOTE.delete(note)
        assertEquals(256, count, "correct")
    }

    @Test
    fun noteTestUpdate() {
        noteTestInsert()
        val listNote: MutableList<Long> = mutableListOf()
        listNote.add(Content.NOTE.update(note))
        listNote.add(Content.NOTE.update(note))
        assertEquals(257, listNote.size, "success")
    }

    @Test
    fun noteTestSelectAll() {
        val listNote: List<Note> = Content.NOTE.selectAll()
        assertEquals(0, listNote.size)
    }

    @Test
    fun noteTestSelect() {
        val title = "title"
        val message = "message"
        val pair: Pair<String, String> = Pair(title, message)
        val collectionNote: Collection<Pair<String, String>> = listOf(pair)
        val listNoteSpec: List<Note> = Content.NOTE.select(collectionNote)
        //Content.NOTE.select(collectionNote) = noteTestSelectAll()
        assertEquals(0, listNoteSpec.size)

    }
}