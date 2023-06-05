package com.example.contactsapp.application.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.emailId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.genderId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.locationId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.nameId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.phoneId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.pictureId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.usernameId
import com.example.contactsapp.application.view.shared.setImageOfContact
import com.example.contactsapp.databinding.ContactDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ContactDetailFragment : Fragment() {

    private var _binding: ContactDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ContactDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataOfContact()
    }

    private fun setDataOfContact() {
        arguments?.let { data ->
            binding.profileImageView.setImageOfContact(data.getString(pictureId) ?: "",600,600)
            binding.textName.text = data.getString(nameId) ?: ""
            binding.textGender.text = data.getString(genderId) ?: ""
            binding.textEmail.text = data.getString(emailId) ?: ""
            binding.textUsername.text = data.getString(usernameId) ?: ""
            binding.textPhone.text = data.getString(phoneId) ?: ""
            binding.textLocation.text = data.getString(locationId) ?: ""
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}