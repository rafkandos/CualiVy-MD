
package app.project.cualivy_capstone.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.adapter.JobAdapter
import app.project.cualivy_capstone.databinding.ActivityDetailBinding
import app.project.cualivy_capstone.databinding.ActivityListJobBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.recyclerview.ListJobViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

//        val url = intent.getStringExtra("listJob")
//        val url = PreferenceManager.getGuid(this)
//        url?.let {
//            getDetail(url)
//        }

        val guid = PreferenceManager.getGuid()
        if (guid != null) {
            getDetail(guid. guid)
        }


//        val url = intent.getStringExtra(EXTRA_URL)
//        url?.let {
////            loadWebView(url)
//            getDetail("guid")
//        }
        setupView()
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


    private fun getDetail(guid: String) {
        val client = AsyncHttpClient()
        val url = "http://34.124.223.74/api/Job/Detail/$guid"
        val params = RequestParams()
        params.put("guid", guid)

        client.get(url, params,  object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val listJob = ArrayList<String>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result).getJSONObject("data")
                    val guid = jsonObject.getString("guid")
                    val kindOfWork = jsonObject.getString("kindofwork")
                    val position = jsonObject.getString("position")
                    val companyName = jsonObject.getString("companyname")
                    val location = jsonObject.getString("location")
                    val education = jsonObject.getString("education")
                    val major = jsonObject.getString("major")
                    val description = jsonObject.getString("description")
                    val minimumYears = jsonObject.getString("minimumyears")
                    val skills = jsonObject.getString("skills")
                    val link = jsonObject.getString("link")
                    val notes = jsonObject.getString("notes")
                    val thirdParty = jsonObject.getString("thirdparty")
                    val image = jsonObject.getString("image")

                    listJob.add("$guid\n - $kindOfWork-$position\n - $companyName\n - $location\n - $education\n -$major\n - $description\n - $minimumYears\n - $skills - $link - $notes\n  -$thirdParty\n-$image")

                    // Tampilkan data pada view
                    binding.tvDetail.text = listJob.joinToString("\n")

                } catch (e: JSONException) {
                    Toast.makeText(this@DetailActivity, "Error parsing JSON", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@DetailActivity, "$statusCode : ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_URL = "extra_url"
    }

//    private fun loadWebView(url: String) {
//        webView.loadUrl(url)
//    }
//
//    companion object {
//        const val EXTRA_NAME = "extra_name"
//        const val EXTRA_URL = "extra_url"
//    }
}



//        viewModel.isLoading.observe(this) { isLoading ->
//            if (isLoading) {
//                binding.progressBar.visibility = View.VISIBLE
//            } else {
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//        }
//        viewModel.getListJob(url)
//        viewModel.listJob.observe(this) { listJob ->
//            val adapter = JobAdapter(listJob)
//            binding.tvDetail.adapter = adapter
//        }
//    }
