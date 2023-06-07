package com.example.contactsapp.infrastructure.dblocal

import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.FilterGender
import com.example.contactsapp.domain.models.FilterOrder
import com.example.contactsapp.domain.repositories.ContactRepositoryDbLocal
import com.example.contactsapp.infrastructure.network.vos.ContactVo
import javax.inject.Inject

class ContactRepositoryRoom @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepositoryDbLocal {

    override suspend fun getContactFilterByGender(filterGender: FilterGender): List<Contact> {
        return contactDao.getContactFilterByGender(
            when {
                filterGender.male -> "male"
                filterGender.female -> "female"
                else -> "female"
            }
        ).toContactListDomain()
    }

    override suspend fun getContactFilterByOrder(filterOrder: FilterOrder): List<Contact> {
        return when {
            filterOrder.last -> contactDao.getContactSortedByLastName().toContactListDomain()
            filterOrder.age -> contactDao.getContactSortedByAge().toContactListDomain()
            else -> contactDao.getContactSortedByAge().toContactListDomain()
        }
    }

    override suspend fun addAllContacts(contacts: List<ContactVo>) {
        contactDao.insertAll(contacts.toContactListDataBase())
    }
}