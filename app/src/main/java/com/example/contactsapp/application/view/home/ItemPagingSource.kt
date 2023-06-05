package com.example.contactsapp.application.view.home

import androidx.paging.PagingSource
import com.example.contactsapp.domain.models.Contact

interface ItemPagingSource {
    fun loadItems(pageSize: Int): PagingSource<Int, Contact>
}