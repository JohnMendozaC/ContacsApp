package com.example.contactsapp.domain.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.FilterGender
import com.example.contactsapp.domain.models.FilterOrder
import com.example.contactsapp.domain.repositories.ContactsService
import com.example.contactsapp.infrastructure.network.anticorruption.toContactList
import com.example.contactsapp.infrastructure.network.daos.ContactsDaoRetroFit
import com.example.contactsapp.infrastructure.network.response.ContactsResponse
import javax.inject.Inject

class ContactsServiceImpl @Inject constructor(
    private val contactsDaoRetroFit: ContactsDaoRetroFit
) : PagingSource<Int, Contact>() {

    private var isApplyFilter: Boolean = false
    private val contacts: ArrayList<Contact> = ArrayList()
    private var contactsWithFilter: ArrayList<Contact> = ArrayList()

    fun applyFilters(filterOrder: FilterOrder, filterGender: FilterGender) {
        isApplyFilter = true

        val contactsOrder: List<Contact> = when {
            filterOrder.age -> {
                contacts.sortedBy { it.name }
            }

            filterOrder.last -> {
                contacts.sortedBy { it.username }
            }
            else -> {
                contacts
            }
        }

        val contactsGender = when {
            filterGender.male -> {
                contactsOrder.filter { it.gender == "male" }
            }

            filterGender.female -> {
                contactsOrder.filter { it.gender == "female" }
            }
            else -> {
                contactsOrder
            }
        }
        contactsWithFilter.addAll(contactsGender)
        invalidate()
        isApplyFilter = false
    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val pageNumber = params.key ?: 1
        return if (isApplyFilter) {
            loadDataWithFilter(pageNumber)
        } else {
            loadDataOfApi(pageNumber)
        }
    }

    private fun loadDataWithFilter(pageNumber: Int): LoadResult.Page<Int, Contact> {
        val prevKey = if (pageNumber > 1) pageNumber - 1 else null
        val nextKey = if (contactsWithFilter.isNotEmpty()) pageNumber + 1 else null
        return LoadResult.Page(contactsWithFilter, prevKey, nextKey)
    }

    private suspend fun loadDataOfApi(pageNumber: Int) =
        try {
            when (val response =
                ContactsResponse.validateResponse(contactsDaoRetroFit.getContactsByMax(20))) {
                is ContactsResponse.Success -> {
                    contacts.addAll(response.response.results.toContactList())
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