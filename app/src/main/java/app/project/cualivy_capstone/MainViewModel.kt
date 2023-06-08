package app.project.cualivy_capstone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.preference.UserPreference
import app.project.cualivy_capstone.response.Login
import kotlinx.coroutines.launch

class MainViewModel (private val pref:PreferenceManager): ViewModel() {
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): String?{
        return pref.getToken()
    }

    fun login(user : Login) {
        viewModelScope.launch {
            pref.login(user)
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}