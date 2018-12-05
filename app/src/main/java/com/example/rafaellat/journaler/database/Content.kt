package com.example.rafaellat.journaler.database

import android.content.ContentValues
import android.location.Location
import android.net.Uri
import android.util.Log
import com.example.rafaellat.journaler.Journaler
import com.example.rafaellat.journaler.model.Entry
import com.example.rafaellat.journaler.model.Note
import com.example.rafaellat.journaler.model.Todo
import com.example.rafaellat.journaler.provider.JournalerProvider
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import kotlin.reflect.KClass


object Content {

    private val tag = "Content"
    private val gson = Gson()

    private val version = 1
    private val name = "students"

    val NOTE = object : Crud<Note> {

        override fun insert(what: Note): Long {
            val inserted = insert(listOf(what))
            if (!inserted.isEmpty()) return inserted[0]
            return 0
        }

        override fun insert(what: Collection<Note>): List<Long> {
            val ids = mutableListOf<Long>()
            what.forEach { item ->
                val values = ContentValues()
                values.put(DbHelper.COLUMN_TITLE, item.title)
                values.put(DbHelper.COLUMN_MESSAGE, item.message)
                values.put(
                    DbHelper.COLUMN_LOCATION,
                    gson.toJson(item.location)
                )
                val uri = Uri.parse(JournalerProvider.URL_NOTE)
                val ctx = Journaler.ctx
                ctx?.let {
                    val result = ctx.contentResolver.insert(uri, values)
                    result?.let {
                        try {
                            ids.add(result.lastPathSegment.toLong())
                        } catch (e: Exception) {
                            Log.e(tag, "Error: $e")
                        }
                    }
                }
            }
            return ids
        }

        override fun update(what: Note) = update(listOf(what))

        override fun update(what: Collection<Note>): Long {
            var count = 0L
            what.forEach { item ->
                val values = ContentValues()
                values.put(DbHelper.COLUMN_TITLE, item.title)
                values.put(DbHelper.COLUMN_MESSAGE, item.message)
                values.put(
                    DbHelper.COLUMN_LOCATION,
                    gson.toJson(item.location)
                )
                val uri = Uri.parse(JournalerProvider.URL_NOTE)
                val ctx = Journaler.ctx
                ctx?.let {
                    count += ctx.contentResolver.update(
                        uri, values, "_id = ?", arrayOf(item.id.toString())
                    )
                }
            }
            return count
        }

        override fun delete(what: Note): Int= delete(listOf(what))

        override fun deleteById(id:Long): Int= delete(listOf(findById(id)))

        override fun findById(id: Long): Note {

            val note: Note? = listOf<Note>().find { it.id == id }

            return note!!
        }

        override fun delete(what: Collection<Note>): Int {
            var count = 0
            what.forEach { item ->
                val uri = Uri.parse(JournalerProvider.URL_NOTE)
                val ctx = Journaler.ctx
                ctx?.let {
                    count += ctx.contentResolver.delete(
                        uri, "_id = ?", arrayOf(item.id.toString())
                    )
                }
            }
            return count
        }

        override fun select(args: Pair<String, String>): List<Note> = select(listOf(args))

        override fun select(args: Collection<Pair<String, String>>): List<Note> {
            val items = mutableListOf<Note>()
            val selection = StringBuilder()
            val selectionArgs = mutableListOf<String>()
            args.forEach { arg ->
                selection.append("${arg.first} == ?")
                selectionArgs.add(arg.second)
            }
            val ctx = Journaler.ctx
            ctx?.let {
                val uri = Uri.parse(JournalerProvider.URL_NOTES)
                val cursor = ctx.contentResolver.query(
                    uri, null, selection.toString(), selectionArgs.toTypedArray(), null
                )
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.ID))
                    val titleIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE)
                    val title = cursor.getString(titleIdx)
                    val messageIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MESSAGE)
                    val message = cursor.getString(messageIdx)
                    val locationIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATION)
                    val locationJson = cursor.getString(locationIdx)
                    val location = gson.fromJson<Location>(locationJson)
                    val note = Note(title, message, location)
                    note.id = id
                    items.add(note)
                }
                cursor.close()
                return items
            }
            return items
        }

        override fun selectAll(): List<Note> {
            val items = mutableListOf<Note>()
            val ctx = Journaler.ctx
            ctx?.let {
                val uri = Uri.parse(JournalerProvider.URL_NOTES)
                val cursor = ctx.contentResolver.query(
                    uri, null, null, null, null
                )
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.ID))
                    val titleIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE)
                    val title = cursor.getString(titleIdx)
                    val messageIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MESSAGE)
                    val message = cursor.getString(messageIdx)
                    val locationIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATION)
                    val locationJson = cursor.getString(locationIdx)
                    val location = gson.fromJson<Location>(locationJson)
                    val note = Note(title, message, location)
                    note.id = id
                    items.add(note)
                }
                cursor.close()
            }
            return items
        }

//       override fun getNOTE(){
//            Log.d(tag, "Test get")
//        }
    }


    val TODO = object : Crud<Todo> {
        override fun deleteById(id: Long): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun findById(id: Long): Note {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun insert(what: Todo): Long {
            val inserted = insert(listOf(what))
            if (!inserted.isEmpty()) return inserted[0]
            return 0
        }

        override fun insert(what: Collection<Todo>): List<Long> {
            val ids = mutableListOf<Long>()
            what.forEach { item ->
                val values = ContentValues()
                values.put(DbHelper.COLUMN_TITLE, item.title)
                values.put(DbHelper.COLUMN_MESSAGE, item.message)
                values.put(DbHelper.COLUMN_LOCATION, gson.toJson(item.location))
                val uri = Uri.parse(JournalerProvider.URL_TODO)
                values.put(DbHelper.COLUMN_SCHEDULED, item.scheduledFor)
                val ctx = Journaler.ctx
                ctx?.let {
                    val result = ctx.contentResolver.insert(uri, values)
                    result?.let {
                        try {
                            ids.add(result.lastPathSegment.toLong())
                        } catch (e: Exception) {
                            Log.e(tag, "Error: $e")
                        }
                    }
                }
            }
            return ids
        }

        override fun update(what: Todo) = update(listOf(what))

        override fun update(what: Collection<Todo>): Long {
            var count = 0L
            what.forEach { item ->
                val values = ContentValues()
                values.put(DbHelper.COLUMN_TITLE, item.title)
                values.put(DbHelper.COLUMN_MESSAGE, item.message)
                values.put(DbHelper.COLUMN_LOCATION, gson.toJson(item.location))
                val uri = Uri.parse(JournalerProvider.URL_TODO)
                values.put(DbHelper.COLUMN_SCHEDULED, item.scheduledFor)
                val ctx = Journaler.ctx
                ctx?.let {
                    count += ctx.contentResolver.update(
                        uri, values, "_id = ?", arrayOf(item.id.toString())
                    )
                }
            }
            return count
        }

        override fun delete(what: Todo): Int = delete(listOf(what))

        override fun delete(what: Collection<Todo>): Int {
            var count = 0
            what.forEach { item ->
                val uri = Uri.parse(JournalerProvider.URL_TODO)
                val ctx = Journaler.ctx
                ctx?.let {
                    count += ctx.contentResolver.delete(
                        uri, "_id = ?", arrayOf(item.id.toString())
                    )
                }
            }
            return count
        }

        override fun select(args: Pair<String, String>): List<Todo> = select(listOf(args))

        override fun select(args: Collection<Pair<String, String>>): List<Todo> {
            val items = mutableListOf<Todo>()
            val selection = StringBuilder()
            val selectionArgs = mutableListOf<String>()
            args.forEach { arg ->
                selection.append("${arg.first} == ?")
                selectionArgs.add(arg.second)
            }
            val ctx = Journaler.ctx
            ctx?.let {
                val uri = Uri.parse(JournalerProvider.URL_TODOS)
                val cursor = ctx.contentResolver.query(
                    uri, null, selection.toString(), selectionArgs.toTypedArray(), null
                )
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.ID))
                    val titleIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE)
                    val title = cursor.getString(titleIdx)
                    val messageIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MESSAGE)
                    val message = cursor.getString(messageIdx)
                    val locationIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATION)
                    val locationJson = cursor.getString(locationIdx)
                    val location = gson.fromJson<Location>(locationJson)
                    val scheduledForIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_SCHEDULED)
                    val scheduledFor = cursor.getLong(scheduledForIdx)
                    val todo = Todo(title, message, location, scheduledFor)
                    todo.id = id
                    items.add(todo)
                }
                cursor.close()
            }
            return items
        }

        override fun selectAll(): List<Todo> {
            val items = mutableListOf<Todo>()
            val ctx = Journaler.ctx
            ctx?.let {
                val uri = Uri.parse(JournalerProvider.URL_TODOS)
                val cursor = ctx.contentResolver.query(
                    uri, null, null, null, null
                )
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.ID))
                    val titleIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE)
                    val title = cursor.getString(titleIdx)
                    val messageIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MESSAGE)
                    val message = cursor.getString(messageIdx)
                    val locationIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATION)
                    val locationJson = cursor.getString(locationIdx)
                    val location = gson.fromJson<Location>(locationJson)
                    val scheduledForIdx = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_SCHEDULED)
                    val scheduledFor = cursor.getLong(scheduledForIdx)
                    val todo = Todo(title, message, location, scheduledFor)
                    todo.id = id
                    items.add(todo)
                }
                cursor.close()
            }
            return items
        }

//        override fun getNOTE(){
//            Log.d(tag, "Test get")
//        }
    }
}
