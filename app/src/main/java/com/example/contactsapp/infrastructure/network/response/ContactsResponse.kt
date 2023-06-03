package com.example.contactsapp.infrastructure.network.response

import retrofit2.Response
import java.net.HttpURLConnection

sealed class ContactsResponse<T> {
    data class Success<T>(val response: T) : ContactsResponse<T>()
    data class Error<T>(val codeError: Int = 0) : ContactsResponse<T>()
    companion object {
        fun <T> validateResponse(response: Response<T>): ContactsResponse<T> {
            val isValid = if (response.isSuccessful) {
                when (response.code()) {
                    HttpURLConnection.HTTP_OK -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
            } else {
                false
            }

            return if (isValid) {
                val body = response.body()
                if (body == null) {
                    Error()
                } else {
                    Success(body)
                }
            } else {
                Error()
            }
        }
    }
}