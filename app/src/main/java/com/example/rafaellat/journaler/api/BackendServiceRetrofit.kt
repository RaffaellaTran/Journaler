package com.example.rafaellat.journaler.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BackendServiceRetrofit {
    fun obtain(
        readTimeoutInSeconds: Long = 1,
        connectTimeoutInSeconds: Long = 1
    ): Retrofit {
        val loggingInerceptor = HttpLoggingInterceptor()

        loggingInerceptor.level = HttpLoggingInterceptor.Level.BODY

        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/") //backend base URL set to local host
            .addConverterFactory(GsonConverterFactory.create()) // converter factory as a mechanism for JSON deseralization
            .client(
                OkHttpClient // create a new OkHttp
                    .Builder()
                    .addInterceptor(loggingInerceptor)
                    .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
                    .build()
            )
            .build()
    }
}