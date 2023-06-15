package app.project.cualivy_capstone.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.response.Token
import kotlinx.coroutines.launch

class MainViewModel (private val pref:PreferenceManager): ViewModel() {
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): Token {
        return pref.getToken()
    }

    fun getLogout(){
        return pref.logOut()
    }
    fun login(user : Token) {
        viewModelScope.launch {
            pref.saveToken(user)
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}