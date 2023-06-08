package app.project.cualivy_capstone.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import app.project.cualivy_capstone.MainActivity
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.ViewModelFactory
import app.project.cualivy_capstone.databinding.ActivityLoginBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.register.RegisterActivity
import app.project.cualivy_capstone.response.Login

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()

        binding.accountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        val pref = PreferenceManager.getInstance(this)
        loginViewModel = ViewModelProvider(
            this,
                    ViewModelFactory(pref)
        ).get(LoginViewModel::class.java)

        loginViewModel.error.observe(this) { error ->
            loginViewModel.message.observe(this) { message ->
                if (!error) {

                    loginViewModel.login.observe(this) { loginResult ->
                        val status = loginResult.status
                        val message = loginResult.message
                        val data = loginResult.data
                        val totalData = loginResult.totaldata

                        val user = Login(status, message, data, totalData)
                        loginViewModel.saveUser(user)
                        loginViewModel.login()
                    }
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.login))
                    builder.setMessage(message)
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }, 2000L)
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.login))
                    builder.setMessage(message)
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, 2000L)
                }
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }



    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = getString(R.string.empty_email)
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = getString(R.string.empty_password)
                }
                password.length < 8 -> {
                   binding.passwordEditText.error = getString(R.string.invalid_password)
               }
                else -> {
                  loginViewModel.login(email, password)
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                        finish()
//                    }, 2000L)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}