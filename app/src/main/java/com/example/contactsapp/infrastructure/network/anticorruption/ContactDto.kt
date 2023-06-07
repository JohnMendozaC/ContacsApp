package com.example.contactsapp.infrastructure.network.anticorruption

import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.Picture
import com.example.contactsapp.infrastructure.network.vos.ContactVo
import com.example.contactsapp.infrastructure.network.vos.PictureVo

fun List<ContactVo>.toContactList() = this.map {
    Contact(
        picture = it.picture.toPicture(),
        gender = it.gender,
        name = "${it.name.title} ${it.name.first} ${it.name.last}",
        username = it.login.username,
        email = it.email,
        phone = it.phone,
        location = "${it.location.street.name} ${it.location.city} ${it.location.state} ${it.location.postcode}"
    )
}

private fun PictureVo.toPicture() = Picture(
    large = this.large,
    medium = this.medium
)