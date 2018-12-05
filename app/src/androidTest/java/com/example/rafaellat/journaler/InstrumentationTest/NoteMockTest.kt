package com.example.rafaellat.journaler.InstrumentationTest

import android.location.Location
import androidx.appcompat.R.id.title
import com.example.rafaellat.journaler.database.Content
import com.example.rafaellat.journaler.model.Note
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class NoteMockTest() {

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks

    }
    @SpyK //    @RelaxedMockK,  @SpyK
    // @InjectMockK by default accept laterinit var or var
    var note = Note(
        "stub ${System.currentTimeMillis()}", "stub ${System.currentTimeMillis()}", Location("Stub")
    )
    @Test
    fun noteTestInsert() {

      //  var content = mockk<Note>(relaxed = true)
      //  mockkObject(note)
        every {
            note.id = Content.NOTE.insert(note)
        }answers { println("Note id:" + note.id) }

        //assert(note.id == 100L)
          assertEquals(1L, note.id)

    }


//    @Test
//    fun noteTestDelete() {
//
//        var count = 1
//        every {  count = Content.NOTE.deleteById(1)}
//        assertEquals(0, count, "correct")
//    }


//    val noteUp = mockk<Note>(relaxed = true)
//    @Test
//    fun noteTestUpdate() {
//        verify { Content.NOTE.update(noteUp)}
//        noteTestInsert()
//        note2.id = Content.NOTE.update(note)
//        val count = Content.NOTE.delete(note)
//        verify { count }
    //assert(count)
    //  }
}

data class NoteData(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val location: Location
)


