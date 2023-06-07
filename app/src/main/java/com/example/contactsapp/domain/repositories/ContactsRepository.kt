package com.example.contactsapp.domain.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.infrastructure.network.anticorruption.toContactList
import com.example.contactsapp.infrastructure.network.daos.ContactsDaoRetroFit
import com.example.contactsapp.infrastructure.network.response.ContactsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsDaoRetroFit: ContactsDaoRetroFit,
    private val contactDbLocal: ContactRepositoryDbLocal
) : PagingSource<Int, Contact>() {
    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> =
        try {
            when (val response =
                ContactsResponse.validateResponse(contactsDaoRetroFit.getContactsByMax(20))) {
                is ContactsResponse.Success -> {
                    val pageNumber = params.key ?: 1

                    withContext(Dispatchers.IO) {
                        contactDbLocal.addAllContacts(response.response.results)
                    }
                    val contacts = response.response.results.toContactList()
                    val prevKey = if (pageNumber > 1) pageNumber - 1 else null
                    val nextKey = if (contacts.isNotEmpty()) pageNumber + 1 else null

                    LoadResult.Page(contacts, prevKey, nextKey)
                }

                is ContactsResponse.Error -> {
                    LoadResult.Error(Exception("API request failed"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}