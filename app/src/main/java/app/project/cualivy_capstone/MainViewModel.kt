package app.project.cualivy_capstone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.project.cualivy_capstone.preference.UserPreference
import app.project.cualivy_capstone.response.Login
import kotlinx.coroutines.launch

class MainViewModel (private val pref: UserPreference): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    fun getUser(): LiveData<Login> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}