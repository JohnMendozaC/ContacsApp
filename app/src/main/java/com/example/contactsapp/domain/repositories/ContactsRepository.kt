package com.example.contactsapp.domain.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsService: ContactsService
) {

    suspend fun getContacts() = withContext(Dispatchers.IO) {
        contactsService.getContacts()
    }
}