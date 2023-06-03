package com.example.contactsapp.infrastructure.network.daos

import com.example.contactsapp.infrastructure.network.vos.ContactsVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsDaoRetroFit {

    @GET("/?results={numbersContacts}")
    suspend fun getFoodRecipeById(
        @Path("numberContacts") numberContacts: Int
    ): Response<ContactsVo>
}