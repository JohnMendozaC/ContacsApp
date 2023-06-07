package com.example.contactsapp.domain.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.FilterGender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsFilterRepository @Inject constructor(
    private val contactDbLocal: ContactRepositoryDbLocal
) : PagingSource<Int, Contact>() {

    lateinit var filterGender: FilterGender

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> =
        try {
            withContext(Dispatchers.IO) {
                val pageNumber = params.key ?: 1
                val contacts =
                    contactDbLocal.getContactFilterByGender(filterGender)

                val prevKey = if (pageNumber > 1) pageNumber - 1 else null
                val nextKey = if (contacts.isNotEmpty()) pageNumber + 1 else null

                LoadResult.Page(contacts, prevKey, nextKey)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}