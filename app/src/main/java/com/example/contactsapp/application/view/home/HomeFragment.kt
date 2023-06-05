package com.example.contactsapp.application.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.contactsapp.application.viewmodels.HomeViewModel
import com.example.contactsapp.application.viewmodels.StatusHomeView
import com.example.contactsapp.databinding.ContactsHomeBinding
import com.example.contactsapp.domain.models.Contact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: ContactsHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
    }

    private fun subscribeUi() {
        binding.rvContacts.adapter =
            ContactsAdapter() // Configurar el adaptador antes de recopilar los datos

        lifecycleScope.launch {
            homeViewModel.contacts.collectLatest { contacts ->
                (binding.rvContacts.adapter as ContactsAdapter).submitData(contacts)
                binding.rvContacts.visibility = View.VISIBLE
            }
        }
    }

    private fun validateStatusHomeView(status: StatusHomeView) {
        when (status) {
            is StatusHomeView.ShowLoader -> {
                showLoader(status.isLoading)
            }

            is StatusHomeView.ShowContacts -> {
                showContacts(status.contacts)
            }

            is StatusHomeView.ShowError -> {
                showError()
            }
        }
    }

    private fun showLoader(isLoading: Boolean) {
//        binding.loaderContacts.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showContacts(contacts: List<Contact>) {
//        (binding.rvContacts.adapter as ContactsAdapter).submitData(contacts)
//        binding.notFountData.visibility = View.GONE
        binding.rvContacts.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.rvContacts.visibility = View.GONE
//        binding.notFountData.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}