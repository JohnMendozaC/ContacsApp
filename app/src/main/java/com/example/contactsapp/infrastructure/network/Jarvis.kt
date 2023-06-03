package com.example.contactsapp.infrastructure.network

import android.util.Base64

object Jarvis {

    fun getDecodeData(dataEncode: String): String {
        val decodedBytes = Base64.decode(dataEncode, Base64.DEFAULT)
        return String(decodedBytes)
    }
}