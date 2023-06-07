package com.example.contactsapp.infrastructure.dblocal

import com.example.contactsapp.domain.models.Contact
import com.example.contactsapp.domain.models.Picture
import com.example.contactsapp.infrastructure.network.vos.ContactVo

fun List<ContactVo>.toContactListDataBase() = this.map {
    ContactEntity(
        pictureLarge = it.picture.large,
        pictureMedium = it.picture.medium,
        gender = it.gender,
        tittleName = it.name.title,
        firstName = it.name.first,
        lastName = it.name.last,
        username = it.login.username,
        email = it.email,
        phone = it.phone,
        age = it.registered.age,
        location = "${it.location.street.name} ${it.location.city} ${it.location.state} ${it.location.postcode}"
    )
}

fun List<ContactEntity>.toContactListDomain() = this.map {
    Contact(
        picture = Picture(large = it.pictureLarge, medium = it.pictureMedium),
        gender = it.gender,
        name = "${it.tittleName} ${it.firstName} ${it.lastName}",
        username = it.username,
        email = it.email,
        phone = it.phone,
        location = it.location
    )
}