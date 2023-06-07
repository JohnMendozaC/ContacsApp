package com.example.contactsapp.application.view.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.contactsapp.application.viewmodels.HomeViewModel
import com.example.contactsapp.databinding.ContactsHomeBinding
import com.example.contactsapp.databinding.FilterOverlayBinding
import com.example.contactsapp.databinding.SortByOverlayBinding
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
        binding.rvContacts.adapter = ContactsAdapter()

        homeViewModel.getAllContacts()

        binding.buttonFilter.setOnClickListener {
            showFilterPopup()
        }

        binding.buttonSortBy.setOnClickListener {
            showSortPopup()
        }

        refreshContacts()
        validateLoadState()
    }

    private fun refreshContacts() {
        lifecycleScope.launch {
            homeViewModel.contacts.collectLatest { contacts ->
                (binding.rvContacts.adapter as ContactsAdapter).submitData(contacts)
            }
        }
    }

    private fun validateLoadState() {
        (binding.rvContacts.adapter as ContactsAdapter).addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    showLoader(true)
                }
                is LoadState.Error -> {
                    showLoader(false)
                    showError()
                }
                else -> {
                    showLoader(false)
                    showContacts()
                }
            }
        }
    }

    private fun showFilterPopup() {
        val binding: FilterOverlayBinding = FilterOverlayBinding.inflate(layoutInflater)
        val popupView = binding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        binding.femaleRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterGender.female = isChecked
            homeViewModel.filterGender.male = !isChecked
            homeViewModel.updateFilterGender()
        }

        binding.maleRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterGender.male = isChecked
            homeViewModel.filterGender.female = !isChecked
            homeViewModel.updateFilterGender()
        }

        binding.noneRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterGender.male = !isChecked
            homeViewModel.filterGender.female = !isChecked
            homeViewModel.getAllContacts()
        }

        binding.applyFilterButton.setOnClickListener {
            refreshContacts()
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(binding.root, Gravity.AXIS_CLIP, 0, 0)
    }

    private fun showSortPopup() {
        val binding: SortByOverlayBinding = SortByOverlayBinding.inflate(layoutInflater)
        val popupView = binding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true


        binding.ageRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterOrder.age = isChecked
            homeViewModel.filterOrder.last = !isChecked
            homeViewModel.updateFilterOrder()
        }

        binding.surnameRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterOrder.last = isChecked
            homeViewModel.filterOrder.age = !isChecked
            homeViewModel.updateFilterOrder()
        }

        binding.byDefaultRadioButton.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.filterOrder.last = !isChecked
            homeViewModel.filterOrder.age = !isChecked
            homeViewModel.getAllContacts()
        }

        binding.applySortedButton.setOnClickListener {
            refreshContacts()
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(binding.root, Gravity.AXIS_CLIP, 0, 0)
    }

    private fun showLoader(isLoading: Boolean) {
        binding.loaderContacts.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showContacts() {
        binding.notFountData.visibility = View.GONE
        binding.rvContacts.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.rvContacts.visibility = View.GONE
        binding.notFountData.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}