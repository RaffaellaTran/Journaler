package com.example.rafaellat.journaler.api

//auth credential
data class UserLoginRequest (
    val username: String,
    val password: String
)