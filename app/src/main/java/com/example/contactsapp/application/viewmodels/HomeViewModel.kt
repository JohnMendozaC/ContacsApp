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
import com.example.contactsapp.domain.repositories.ContactsFilterRepository
import com.example.contactsapp.domain.repositories.ContactsOrderRepository
import com.example.contactsapp.domain.repositories.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsOrderRepository: ContactsOrderRepository,
    private val contactsFilterRepository: ContactsFilterRepository
) : ViewModel() {

    lateinit var contacts: Flow<PagingData<Contact>>
    val filterOrder = FilterOrder()
    val filterGender = FilterGender()
    fun getAllContacts() {
        contacts = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            contactsRepository
        }.flow
            .cachedIn(viewModelScope)
    }
    fun updateFilterOrder() {
        contacts = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            contactsOrderRepository.apply {
                filterOrder = this@HomeViewModel.filterOrder
            }
        }.flow
            .cachedIn(viewModelScope)
    }

    fun updateFilterGender() {
        contacts = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            contactsFilterRepository.apply {
                filterGender = this@HomeViewModel.filterGender
            }
        }.flow
            .cachedIn(viewModelScope)
    }
}