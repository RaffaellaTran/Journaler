package com.example.rafaellat.journaler.api

import com.example.rafaellat.journaler.model.Note
import com.example.rafaellat.journaler.model.Todo
import retrofit2.Call
import retrofit2.http.*

interface JournalerBackendService {
    companion object {
        //give an instance using Retrofit
        fun obtain(): JournalerBackendService{
            return BackendServiceRetrofit.obtain().create(JournalerBackendService::class.java)
        }
    }

    @POST("RaffaellaTran/Journaler/authenticate")
    fun login(@HeaderMap headers: Map<String, String>, @Body payload: UserLoginRequest): Call<JournalerApiToken>

    @GET("RaffaellaTran/Journaler/notes")
    fun getNotes(@HeaderMap headers: Map<String, String>): Call<List<Note>>

    @GET("RaffaellaTran/Journaler/todos")
    fun getTodos(@HeaderMap headers: Map<String, String>): Call<List<Todo>>

    @PUT("RaffaellaTran/Journaler/notes")
    fun publishNotes(@HeaderMap headers: Map<String, String>,@Body payload:List<Note> ): Call<Unit>

    @PUT("RaffaellaTran/Journaler/todos")
    fun publishTodos(@HeaderMap headers: Map<String, String>,@Body payload:List<Todo> ): Call<Unit>

    @DELETE("RaffaellaTran/Journaler/notes")
    fun removeNotes(@HeaderMap headers: Map<String, String>,@Body payload:List<Note> ): Call<Unit>

    @DELETE("RaffaellaTran/Journaler/todos")
    fun removeTodos(@HeaderMap headers: Map<String, String>,@Body payload:List<Todo> ): Call<Unit>

}