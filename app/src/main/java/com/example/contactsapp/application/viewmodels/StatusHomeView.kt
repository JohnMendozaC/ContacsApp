package com.example.contactsapp.application.viewmodels

import com.example.contactsapp.domain.models.Contact

sealed class StatusHomeView {
    data class ShowContacts(val contacts: List<Contact>) : StatusHomeView()
    data class ShowLoader(val isLoading: Boolean) : StatusHomeView()
    data class ShowError(val codeError: Int = 0) : StatusHomeView()
}