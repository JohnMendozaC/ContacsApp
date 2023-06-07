package com.example.contactsapp.infrastructure.network.vos

data class ContactsVo(
    val results: List<ContactVo>
)

data class ContactVo(
    val picture: PictureVo,
    val gender: String,
    val name: NameVo,
    val login: LoginVo,
    val email: String,
    val phone: String,
    val registered: RegisteredVo,
    val location: LocationVo
)