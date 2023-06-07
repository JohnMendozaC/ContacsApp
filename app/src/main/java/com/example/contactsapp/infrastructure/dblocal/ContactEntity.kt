package com.example.contactsapp.infrastructure.dblocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class ContactEntity(
    @PrimaryKey
    val username: String,
    val pictureLarge: String,
    val pictureMedium: String,
    val gender: String,
    val tittleName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val age: Int,
    val location: String
)