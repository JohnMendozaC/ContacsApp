package com.example.contactsapp.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.FilterGender
import com.example.contactsapp.domain.models.FilterOrder
import com.example.contactsapp.domain.services.ContactsServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contactsService: ContactsServiceImpl
) : ViewModel() {

    val contacts: Flow<PagingData<Contact>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        contactsService
    }.flow.cachedIn(viewModelScope)

    val filterOrder = FilterOrder()
    val filterGender = FilterGender()


    fun applyFilter() {
        contactsService.applyFilters(filterOrder, filterGender)
    }
}