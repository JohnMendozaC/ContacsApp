package com.example.contactsapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val picture: Picture,
    val gender: String,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val location: String
) : Parcelable