package com.example.contactsapp.application.di

import com.example.contactsapp.domain.repositories.ContactRepositoryDbLocal
import com.example.contactsapp.infrastructure.dblocal.ContactRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ContactsModule {
    @Binds
    abstract fun provideContactRepositoryDbLocal(contactRepositoryRoom: ContactRepositoryRoom): ContactRepositoryDbLocal
}