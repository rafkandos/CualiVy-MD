package app.project.cualivy_capstone

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import app.project.cualivy_capstone.databinding.ActivityApplyBinding
import app.project.cualivy_capstone.databinding.ActivityDetailBinding
import app.project.cualivy_capstone.detail.DetailActivity
import app.project.cualivy_capstone.preference.PreferenceManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

class ApplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
//        getWeb()

        val link= PreferenceManager.getLink()
        if (link != null) {
            getWeb(link.link)
        }
    }

    private fun setupView() {
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

    private fun getWeb(link : String){
        val client = AsyncHttpClient()
        val url = "http://34.124.223.74/api/Job/Detail/$link"
        val params = RequestParams()
        params.put("guid", link)

        client.get(url, params, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(DetailActivity.TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val link = jsonObject.getString("link")

                    val detailText = "- $link"
                    // Tampilkan data pada view
                    runOnUiThread {
                        binding.webView.loadUrl(link)
                        // Set text pada view
                        binding.detailTextView.text = detailText
                    }

                } catch (e: JSONException) {
                    Toast.makeText(this@ApplyActivity, "Error parsing JSON", Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {

                // Jika koneksi gagal
                if (error != null) {
                    Toast.makeText(
                        this@ApplyActivity,
                        "$statusCode : ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
                }
//        val webView = binding.webView
//        webView.loadUrl(link)

    companion object {
        private const val TAG = "ApplyActivity"
    }
    }
