package com.example.contactsapp.application.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.contactsapp.databinding.ContactItemBinding
import com.example.contactsapp.domain.models.Contact

class ContactsAdapter : PagingDataAdapter<Contact, ContactsViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}

private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem == newItem
}