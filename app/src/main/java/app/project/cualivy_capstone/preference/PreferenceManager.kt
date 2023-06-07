package app.project.cualivy_capstone.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun getPosition(): String {
        return sharedPreferences.getString(KEY_POSITION, "") ?: ""
    }

    fun getCompany(): String {
        return sharedPreferences.getString(KEY_COMPANY, "") ?: ""
    }

    fun getLocation(): String {
        return sharedPreferences.getString(KEY_LOCATION, "") ?: ""
    }


    fun getNotes(): String {
        return sharedPreferences.getString(KEY_NOTES, "") ?: ""
    }
    

    fun getThirdParty(): String {
        return sharedPreferences.getString(KEY_THIRD_PARTY, "") ?: ""
    }

    companion object {
        private const val PREF_NAME = "app.project.cualivy_capstone.preference"
        private const val KEY_POSITION = "position"
        private const val KEY_COMPANY = "company"
        private const val KEY_LOCATION = "location"
        private const val KEY_NOTES = "notes"
        private const val KEY_THIRD_PARTY = "thirdParty"

        private var instance: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            if (instance == null) {
                instance = PreferenceManager(context)
            }
            return instance as PreferenceManager
        }
    }
}
