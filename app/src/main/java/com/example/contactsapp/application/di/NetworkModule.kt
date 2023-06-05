package com.example.contactsapp.application.di

import com.example.contactsapp.infrastructure.network.Api
import com.example.contactsapp.infrastructure.network.daos.ContactsDaoRetroFit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideMovieDaoRetrofit(): ContactsDaoRetroFit = Api.create()
}