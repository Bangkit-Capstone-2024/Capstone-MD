package com.cpstn.momee.utils

import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

object AccessToken {

    private const val firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(): String {
        return try {
            val jsonString = Firebase.SERVICE_URL
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