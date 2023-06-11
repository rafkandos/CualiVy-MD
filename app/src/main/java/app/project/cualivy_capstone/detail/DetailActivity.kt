package app.project.cualivy_capstone.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.project.cualivy_capstone.ApplyActivity
import app.project.cualivy_capstone.MainActivity
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.databinding.ActivityDetailBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.response.JobData
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

        val guid = PreferenceManager.getGuid()
        if (guid != null) {
            getDetail(guid.guid)
        }

        setupView()

//        binding.btnApply.setOnClickListener {
//            getApply()
//        }
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
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "http://34.124.223.74/api/Job/Detail/$guid"
//        val params = RequestParams()
//        params.put("guid", guid)

//        Toast.makeText(this@DetailActivity, "uhuyyy: $guid", Toast.LENGTH_SHORT).show()

        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.INVISIBLE
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
                    val minimumYears = jsonObject.getInt("minimumyears")
                    val skills = jsonObject.getString("skills")
                    val link = jsonObject.getString("link")
                    val notes = jsonObject.getString("notes")
                    val thirdParty = jsonObject.getString("thirdparty")
                    val image = jsonObject.getString("image")

                    val detailText = """
                        $guid
                        - $kindOfWork - $position
                        - $companyName
                        - $location
                        - $education
                        - $major
                        - $description
                        - $minimumYears
                        - $skills
                        - $link
                        - $notes
                        - $thirdParty
                        - $image
                    """.trimIndent()

                    binding.tvKind.text = kindOfWork
                    binding.tvPosition.text = position
                    binding.tvCompany.text = companyName
                    binding.tvLocation.text = location
                    binding.tvEducation.text = "Qualification : " + " " + education
                    binding.tvMajor.text = major
                    binding.tvSkills.text = skills
                    binding.tvNotes.text = "Job Type : " + " " + notes
                    binding.tvThirdparty.text = thirdParty

                    binding.btnApply.setOnClickListener {
                        val url = link
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }

                    val rawHtmlText = description
                    val desc = binding.tvDescription

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        desc.text = Html.fromHtml(rawHtmlText, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        @Suppress("DEPRECATION")
                        desc.text = Html.fromHtml(rawHtmlText) as Spanned
                    }

//                    val htmlText = "<b>Hello</b> <i>World</i>"
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        binding.tvDescription.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
//                    } else {
//                        @Suppress("DEPRECATION")
//                        binding.tvDescription.text = Html.fromHtml(htmlText)
//                    }





                    // Tampilkan data pada view
//                    binding.tvDetail.text = detailText
                    Glide.with(this@DetailActivity)
                        .load(image)
                        .into(binding.imageUser)

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

                binding.progressBar.visibility = View.INVISIBLE
                // Jika koneksi gagal
                if (error != null) {
                    Toast.makeText(this@DetailActivity, "$statusCode : ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getApply(){
        val intent = Intent(this@DetailActivity, ApplyActivity::class.java)
        PreferenceManager.saveLink(JobData("", ""))
        startActivity(intent)
    }


    companion object {
        const val TAG = "DetailActivity"
        const val EXTRA_URL = "extra_url"
    }
}
