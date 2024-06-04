package com.cpstn.momee.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object API {
    const val AUTHORIZATION = "Authorization"
    const val MESSAGE = "message"

    const val IMAGE = "image"
}

object Constant {
    const val EMPTY_STRING = ""
    const val SPACE = " "
    const val ZERO_DOUBLE = 0.0
}

object Preference {
    val USER_TOKEN = stringPreferencesKey("user_token")
}