package com.example.upieczona.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    private val okHttpClient = OkHttpClient()

    val api by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UpieczonaApi::class.java)
    }

    companion object {
        const val BASE_URL = "http://www.upieczona.pl/"
    }
}
