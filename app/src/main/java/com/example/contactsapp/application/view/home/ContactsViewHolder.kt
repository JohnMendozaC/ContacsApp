package com.example.contactsapp.application.view.home

import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.application.view.shared.setImageOfContact
import com.example.contactsapp.databinding.ContactItemBinding
import com.example.contactsapp.domain.models.Contact
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.contactsapp.R

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
                    R.id.action_FirstFragment_to_SecondFragment, bundleOf(
                        contactId to contact
                    )
                )
            }
        }
    }

    companion object {
        const val contactId = "contactId"
    }
}