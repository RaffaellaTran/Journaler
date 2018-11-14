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

    @POST("authenticate")
    fun login(@HeaderMap headers: Map<String, String>, @Body payload: UserLoginRequest): Call<JournalerApiToken>

    @GET("notes")
    fun getNotes(@HeaderMap headers: Map<String, String>): Call<List<Note>>

    @GET("todos")
    fun getTodos(@HeaderMap headers: Map<String, String>): Call<List<Todo>>

    @PUT("notes")
    fun publishNotes(@HeaderMap headers: Map<String, String>,@Body payload:List<Note> ): Call<Unit>

    @PUT("todos")
    fun publishTodos(@HeaderMap headers: Map<String, String>,@Body payload:List<Todo> ): Call<Unit>

    @DELETE("notes")
    fun removeNotes(@HeaderMap headers: Map<String, String>,@Body payload:List<Note> ): Call<Unit>

    @DELETE("todos")
    fun removeTodos(@HeaderMap headers: Map<String, String>,@Body payload:List<Todo> ): Call<Unit>

}