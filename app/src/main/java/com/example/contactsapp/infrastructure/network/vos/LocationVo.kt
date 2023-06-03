package com.example.contactsapp.infrastructure.network.vos

data class LocationVo(
    val street: StreetVo,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
)