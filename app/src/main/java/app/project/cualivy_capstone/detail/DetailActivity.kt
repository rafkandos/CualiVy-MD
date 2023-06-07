
package app.project.cualivy_capstone.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import app.project.cualivy_capstone.R


class DetailActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //webView = findViewById(R.id.webView)

//        val url = intent.getStringExtra(EXTRA_URL)
//        url?.let {
//            loadWebView(url)
//        }

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

//    private fun loadWebView(url: String) {
//        webView.loadUrl(url)
//    }
//
//    companion object {
//        const val EXTRA_URL = "extra_url"
//    }
}



//    @SuppressLint("SetJavaScriptEnabled")
//    private fun loadWebViewData(webViewData: String) {
//        webView.settings.javaScriptEnabled = true
//        webView.loadUrl(webViewData)
//    }
//
//    companion object {
//        const val EXTRA_NAME = "extra_name"
//    }
