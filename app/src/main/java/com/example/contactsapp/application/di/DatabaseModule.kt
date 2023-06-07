package com.example.contactsapp.application.di

import android.content.Context
import com.example.contactsapp.infrastructure.dblocal.AppDataBase
import com.example.contactsapp.infrastructure.dblocal.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase =
            AppDataBase.getInstance(context)

    @Provides
    fun provideContactDao(appDataBase: AppDataBase): ContactDao = appDataBase.contactDao()
}