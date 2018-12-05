package com.example.rafaellat.journaler.database

import com.example.rafaellat.journaler.model.Note
import kotlin.reflect.KClass

interface Crud<T> {
    companion object {
        val BROADCAST_ACTION = "com.example.rafaellat.journaler.broadcast.crud"
        val BROADCAST_EXTRAS_KEY_CRUD_OPERATION_RESULT = "crud_result"
    }

    /**
     * Returns the ID of inserted item.
     */
    fun insert(what: T): Long

    /**
     * Returns the list of inserted IDs.
     */
    fun insert(what: Collection<T>): List<Long>

    /**
     * Returns the number of updated items.
     */
    fun update(what: T): Long

    /**
     * Returns the number of updated items.
     */
    fun update(what: Collection<T>): Long

    /**
     * Returns the number of deleted items.
     */
    fun delete(what: T): Int

    /**
     * Returns the number of deleted items.
     */
    fun delete(what: Collection<T>): Int

    /**
     * Returns the list of items.
     */
    fun select(args: Pair<String, String>): List<T>

    /**
     * Returns the list of items.
     */
    fun select(args: Collection<Pair<String, String>>): List<T>

    /**
     * Return the list of items
     */
    fun selectAll(): List<T>

    fun deleteById(id:Long): Int

    fun findById( id: Long): Note

    //fun getNOTE()
}