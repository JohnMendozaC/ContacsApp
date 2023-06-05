package com.example.contactsapp.application.di

import com.example.contactsapp.domain.repositories.ContactsService
import com.example.contactsapp.domain.services.ContactsServiceImpl
import com.example.contactsapp.infrastructure.network.daos.ContactsDaoRetroFit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*@InstallIn(SingletonComponent::class)
@Module
abstract class ContactsModule {

    @Binds
    abstract fun provideContactsService(contactsService: ContactsServiceImpl): ContactsService
}*/