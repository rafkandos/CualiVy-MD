package app.project.cualivy_capstone.process

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.onboarding.OnboardingActivity
import app.project.cualivy_capstone.recyclerview.ListJobActivity
import app.project.cualivy_capstone.splash.SplashActivity

class ProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = (Intent(this, ListJobActivity::class.java))
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)

        setupView()
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

    companion object {
        private const val SPLASH_TIME_OUT = 3500L
    }
}