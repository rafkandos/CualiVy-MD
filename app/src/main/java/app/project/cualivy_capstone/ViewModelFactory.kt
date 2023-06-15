package app.project.cualivy_capstone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.project.cualivy_capstone.login.LoginViewModel
import app.project.cualivy_capstone.main.MainViewModel
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.register.RegisterViewModel

class ViewModelFactory(private val pref : PreferenceManager) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}