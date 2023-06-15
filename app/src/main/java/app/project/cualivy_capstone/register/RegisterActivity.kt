package app.project.cualivy_capstone.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import androidx.lifecycle.ViewModelProvider
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.databinding.ActivityRegisterBinding
import app.project.cualivy_capstone.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()

        binding.accountTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
        registerViewModel = ViewModelProvider(
            this
        )[RegisterViewModel::class.java]

        registerViewModel.error.observe(this) { error ->
            registerViewModel.message.observe(this) { message ->
                if (message == "Created") {

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.register))
                    builder.setMessage(message)
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }, 2000L)
                } else {

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.register))
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

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {

            val fullname = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                fullname.isEmpty() -> {
                    binding.nameEditText.error = getString(R.string.empty_name)
                }
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

                    registerViewModel.register(fullname, email, password)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}