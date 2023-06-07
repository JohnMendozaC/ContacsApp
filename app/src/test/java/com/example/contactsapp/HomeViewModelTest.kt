package com.example.contactsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.contactsapp.application.viewmodels.HomeViewModel
import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.Picture
import com.example.contactsapp.domain.repositories.ContactsFilterRepository
import com.example.contactsapp.domain.repositories.ContactsOrderRepository
import com.example.contactsapp.domain.repositories.ContactsRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var mockContactsRepository: ContactsRepository

    @MockK(relaxed = true)
    lateinit var contactsOrderRepository: ContactsOrderRepository

    @MockK(relaxed = true)
    lateinit var contactsFilterRepository: ContactsFilterRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(
            mockContactsRepository,
            contactsOrderRepository,
            contactsFilterRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllContacts should return a list of contacts`() = runTest {
        val expectedContacts = listOf(
            createDummyContact(),
            createDummyContact()
        )

        coEvery { mockContactsRepository.registerInvalidatedCallback(any()) } just Runs
        coEvery { mockContactsRepository.getRefreshKey(any()) } returns null
        coEvery { mockContactsRepository.load(any()) } returns PagingSource.LoadResult.Page(
            expectedContacts,
            null,
            null
        )

        val expected = PagingSource.LoadResult.Page(
            data = expectedContacts,
            prevKey = null,
            nextKey = null
        )

        val actual = mockContactsRepository.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assert(expected == actual)
    }


    @Test
    fun `updateFilterOrder should return a list of contacts sorted by name`() = runTest {
        val expectedContacts = listOf(
            createDummyContact(),
            createDummyContact()
        )

        coEvery { contactsOrderRepository.registerInvalidatedCallback(any()) } just Runs
        coEvery { contactsOrderRepository.getRefreshKey(any()) } returns null
        coEvery { contactsOrderRepository.load(any()) } returns PagingSource.LoadResult.Page(
            expectedContacts,
            null,
            null
        )

        val expected = PagingSource.LoadResult.Page(
            data = expectedContacts,
            prevKey = null,
            nextKey = null
        )

        val actual = contactsOrderRepository.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assert(expected == actual)
    }

    @Test
    fun `updateFilterGender should return a list of contacts filtered by gender`() = runTest {
        val expectedContacts = listOf(
            createDummyContact(),
            createDummyContact()
        )

        coEvery { contactsFilterRepository.registerInvalidatedCallback(any()) } just Runs
        coEvery { contactsFilterRepository.getRefreshKey(any()) } returns null
        coEvery { contactsFilterRepository.load(any()) } returns PagingSource.LoadResult.Page(
            expectedContacts,
            null,
            null
        )

        val expected = PagingSource.LoadResult.Page(
            data = expectedContacts,
            prevKey = null,
            nextKey = null
        )

        val actual = contactsFilterRepository.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assert(expected == actual)
    }

    private fun createDummyContact(): Contact {
        val picture = Picture("https://example.com/image.jpg", "https://example.com/image.jpg")
        val gender = "Female"
        val name = "Jane Smith"
        val username = "jane.smith"
        val email = "jane.smith@example.com"
        val phone = "1234567890"
        val location = "New York"

        return Contact(picture, gender, name, username, email, phone, location)
    }
}