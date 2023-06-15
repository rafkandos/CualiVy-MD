package app.project.cualivy_capstone.preference



//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.intPreferencesKey
//import androidx.datastore.preferences.core.stringPreferencesKey
//import app.project.cualivy_capstone.response.Login
//import app.project.cualivy_capstone.response.Token
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
class UserPreference {
//
//    private val gson = Gson()
//
//    fun getUser(): Flow<Login> {
//        return dataStore.data.map { preferences ->
//            Login(
//                preferences[STATUS_KEY] ?: 0,
//                preferences[MESSAGE_KEY] ?: "",
//                gson.fromJson(preferences[DATA_KEY], object : TypeToken<List<Token>>() {}.type)
//                    ?: listOf(),
//                preferences[TOTAL_DATA] ?: 5
//            )
//        }
//    }
//
//    suspend fun saveUser(user: Login) {
//        dataStore.edit { preferences ->
//            preferences[STATUS_KEY] = user.status
//            preferences[MESSAGE_KEY] = user.message
//            preferences[DATA_KEY] = gson.toJson(user.data)
//            preferences[TOTAL_DATA] = user.totaldata
//        }
//    }
//    suspend fun login(user: Login) {
//        dataStore.edit { preferences ->
//            preferences[DATA_KEY] = gson.toJson(user)
//        }
//    }
//    suspend fun saveToken(token: String) {
//        dataStore.edit { preferences ->
//            preferences[KEY_TOKEN] = token
//        }
//    }
//
//    fun getToken(): Flow<String?> {
//        return dataStore.data.map { preferences ->
//            preferences[KEY_TOKEN]
//        }
//    }
//
//
//    companion object {
//        @Volatile
//        private var INSTANCE: UserPreference? = null
//
//        private val STATUS_KEY = intPreferencesKey("status")
//        private val MESSAGE_KEY = stringPreferencesKey("message")
//        private val DATA_KEY = stringPreferencesKey("data")
//        private val TOTAL_DATA = intPreferencesKey("totaldata")
//        private const val KEY_BASE64_IMAGE = "base64Image"
//        private const val KEY_TOKEN = "token"
//
//        fun saveBase64Image(context: Context, base64Image: String) {
//            val sharedPreferences =
//                context.getSharedPreferences(PreferenceManager.PREF_NAME, Context.MODE_PRIVATE)
//            sharedPreferences.edit().apply {
//                putString(PreferenceManager.KEY_BASE64_IMAGE, base64Image)
//            }.apply()
//        }
//
//        fun getBase64Image(context: Context): String? {
//            val sharedPreferences =
//                context.getSharedPreferences(PreferenceManager.PREF_NAME, Context.MODE_PRIVATE)
//            return sharedPreferences.getString(PreferenceManager.KEY_BASE64_IMAGE, null)
//        }
//
//        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserPreference(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
//
//    }
}
