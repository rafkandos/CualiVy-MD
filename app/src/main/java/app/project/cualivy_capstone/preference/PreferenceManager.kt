package app.project.cualivy_capstone.preference

import android.content.Context
import android.content.SharedPreferences
import app.project.cualivy_capstone.response.Login
import app.project.cualivy_capstone.response.Token
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PreferenceManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()


    fun getUser(): Flow<Login> = flow {
        val status = sharedPreferences.getInt(STATUS_KEY, 0)
        val message = sharedPreferences.getString(MESSAGE_KEY, "") ?: ""
        val dataJson = sharedPreferences.getString(DATA_KEY, null)
        val data = gson.fromJson<List<Token>>(
            dataJson,
            object : TypeToken<List<Token>>() {}.type
        ) ?: listOf()
        val totalData = sharedPreferences.getInt(TOTAL_DATA, 5)

        emit(Login(status, message, data, totalData))
    }



    fun saveUser(user: Login) {
        sharedPreferences.edit().apply {
            putInt(STATUS_KEY, user.status)
            putString(MESSAGE_KEY, user.message)
            putString(DATA_KEY, gson.toJson(user.data))
            putInt(TOTAL_DATA, user.totaldata)
        }.apply()
    }

    fun login(user: Login) {
        sharedPreferences.edit().apply {
            putString(DATA_KEY, gson.toJson(user))
        }.apply()
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().apply {
            putString(KEY_TOKEN, token)
        }.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    companion object {
        private const val PREF_NAME = "app.project.cualivy_capstone.preference"
        private const val STATUS_KEY = "status"
        private const val MESSAGE_KEY = "message"
        private const val DATA_KEY = "data"
        private const val TOTAL_DATA = "totaldata"
        private const val KEY_BASE64_IMAGE = "base64Image"
        private const val KEY_TOKEN = "token"

        private var instance: PreferenceManager? = null



        fun saveBase64Image(context: Context, base64Image: String) {
            val sharedPreferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString(KEY_BASE64_IMAGE, base64Image)
            }.apply()
        }

        fun getBase64Image(context: Context): String? {
            val sharedPreferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_BASE64_IMAGE, null)
        }

        fun getInstance(context: Context): PreferenceManager {
            if (instance == null) {
                instance = PreferenceManager(context.applicationContext)
            }
            return instance as PreferenceManager
        }
    }
}
