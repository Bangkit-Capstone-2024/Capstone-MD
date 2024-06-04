package com.cpstn.momee.preference


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.utils.Constant.EMPTY_STRING
import com.cpstn.momee.utils.Preference.USER_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference(private val dataStore: DataStore<Preferences>) {

    fun getUserSession(): Flow<UserDataPreference> {
        return dataStore.data.map { preference ->
            UserDataPreference(
                userToken = preference[USER_TOKEN] ?: EMPTY_STRING,
            )
        }
    }

    suspend fun saveUserSession(userToken: String? = null) {
        dataStore.edit { preference ->
            userToken?.let { preference[USER_TOKEN] = it }
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN)
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