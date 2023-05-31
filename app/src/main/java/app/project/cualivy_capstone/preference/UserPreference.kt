package app.project.cualivy_capstone.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import app.project.cualivy_capstone.response.Login
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Objects

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<Login> {
        return dataStore.data.map { preferences ->
            Login(
                preferences[STATUS_KEY] ?: 0,
                preferences[MESSAGE_KEY] ?: "",
                preferences[DATA_KEY] ?: object : Any() {},
                preferences[LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: Login) {
        dataStore.edit { preferences ->
            preferences[STATUS_KEY] = user.status
            preferences[MESSAGE_KEY] = user.message
            preferences[DATA_KEY] = user.data.toString()
            preferences[LOGIN_KEY] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = true
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val STATUS_KEY = intPreferencesKey("status")
        private val MESSAGE_KEY = stringPreferencesKey("message")
        private val DATA_KEY = stringPreferencesKey("data")
        private val LOGIN_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}