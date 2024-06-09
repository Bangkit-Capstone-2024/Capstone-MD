package com.cpstn.momee.utils

import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

class AccessToken {

    private val firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(): String {
        return try {
            val jsonString = ""
            val inputStream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
            val googleCredential = GoogleCredentials.fromStream(inputStream).createScoped(arrayListOf(firebaseMessagingScope))
            googleCredential.refresh()
            googleCredential.accessToken.tokenValue
        } catch (e: IOException) {
            e.printStackTrace()
            Constant.EMPTY_STRING
        }
    }
}