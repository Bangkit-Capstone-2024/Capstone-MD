package com.cpstn.momee.preference


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.utils.Constant.EMPTY_STRING
import com.cpstn.momee.utils.Preference.HAS_SHOW_ONBOARDING
import com.cpstn.momee.utils.Preference.USER_EMAIL
import com.cpstn.momee.utils.Preference.USER_NAME
import com.cpstn.momee.utils.Preference.USER_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference(private val dataStore: DataStore<Preferences>) {

    fun getUserSession(): Flow<UserDataPreference> {
        return dataStore.data.map { preference ->
            UserDataPreference(
                userToken = preference[USER_TOKEN] ?: EMPTY_STRING,
                userName = preference[USER_NAME] ?: EMPTY_STRING,
                userEmail = preference[USER_EMAIL] ?: EMPTY_STRING,
                hasShowOnboarding  = preference[HAS_SHOW_ONBOARDING] ?: false,
            )
        }
    }

    suspend fun saveUserSession(
        userToken: String? = null,
        userName: String? = null,
        userEmail: String? = null,
        hasShowOnboarding: Boolean? = null
    ) {
        dataStore.edit { preference ->
            userToken?.let { preference[USER_TOKEN] = it }
            userName?.let { preference[USER_NAME] = it }
            userEmail?.let { preference[USER_EMAIL] = it }
            hasShowOnboarding?.let { preference[HAS_SHOW_ONBOARDING] = it }
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN)
            preferences.remove(USER_NAME)
            preferences.remove(USER_EMAIL)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}