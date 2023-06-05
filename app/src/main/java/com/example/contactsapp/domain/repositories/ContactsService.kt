package com.example.contactsapp.domain.repositories

import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.infrastructure.network.response.ContactsResponse

interface ContactsService {
    suspend fun getContacts() : ContactsResponse<List<Contact>>
}