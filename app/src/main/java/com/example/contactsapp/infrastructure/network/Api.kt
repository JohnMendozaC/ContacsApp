package com.example.contactsapp.infrastructure.network

import com.example.contactsapp.BuildConfig.BASE_URL
import com.example.contactsapp.infrastructure.network.daos.ContactsDaoRetroFit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    fun create(): ContactsDaoRetroFit {

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val baseUrl = Jarvis.getDecodeData(BASE_URL)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactsDaoRetroFit::class.java)
    }
}