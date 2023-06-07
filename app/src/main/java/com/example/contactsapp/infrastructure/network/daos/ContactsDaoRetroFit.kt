package com.example.contactsapp.infrastructure.network.daos

import com.example.contactsapp.infrastructure.network.vos.ContactsVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsDaoRetroFit {

    @GET("/")
    suspend fun getContactsByMax(
        @Query("results") numberContacts: Int
    ): Response<ContactsVo>
}