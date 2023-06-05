package com.example.contactsapp.application.view.home

import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.application.view.shared.setImageOfContact
import com.example.contactsapp.databinding.ContactItemBinding
import com.example.contactsapp.domain.models.Contact
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.domain.models.Picture

class ContactsViewHolder(
    private val itemContactBinding: ContactItemBinding
) : RecyclerView.ViewHolder(itemContactBinding.root) {

    fun bind(contact: Contact) {
        itemContactBinding.imageContact.setImageOfContact(contact.picture.medium)
        itemContactBinding.nameContact.text = contact.name
        onClickItemContact(contact)
    }

    private fun onClickItemContact(contact: Contact) {
        with(itemContactBinding.root) {
            setOnClickListener {
                findNavController().navigate(
                    R.id.action_HomeFragment_to_ContactDetailFragment, bundleOf(
                        pictureId to contact.picture.large,
                        nameId to contact.name,
                        genderId to contact.gender,
                        usernameId to contact.username,
                        emailId to contact.email,
                        phoneId to contact.phone,
                        locationId to contact.location
                    )
                )
            }
        }
    }

    companion object {
        const val pictureId = "pictureId"
        const val genderId = "genderId"
        const val nameId = "nameId"
        const val usernameId = "usernameId"
        const val emailId = "emailId"
        const val phoneId = "phoneId"
        const val locationId = "locationId"
    }
}