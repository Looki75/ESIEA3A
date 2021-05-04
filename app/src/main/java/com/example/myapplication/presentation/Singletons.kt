package com.example.myapplication.presentation

import com.example.myapplication.presentation.PokeApplication.Companion.context
import com.example.myapplication.presentation.api.PokeApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class Singletons {
    companion object {
        var cache: Cache = Cache(File(context?.cacheDir, "responses"), 10 * 1024 * 1024) // 10 MiB

        val okttpClient : OkHttpClient = OkHttpClient().newBuilder()
                .cache(cache)
                .build()

        val pokeApi: PokeApi = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                 .client(okttpClient)
                .build()
                .create(PokeApi::class.java)


    }

}

