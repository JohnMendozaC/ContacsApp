package com.example.contactsapp.domain.repositories

import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.FilterGender
import com.example.contactsapp.domain.models.FilterOrder
import com.example.contactsapp.infrastructure.network.vos.ContactVo

interface ContactRepositoryDbLocal {
    suspend fun getContactFilterByGender(filterGender: FilterGender): List<Contact>
    suspend fun getContactFilterByOrder(filterOrder: FilterOrder): List<Contact>
    suspend fun addAllContacts(contacts: List<ContactVo>)
}