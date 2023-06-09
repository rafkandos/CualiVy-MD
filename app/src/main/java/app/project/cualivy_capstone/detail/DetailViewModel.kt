package app.project.cualivy_capstone.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

class DetailViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _listJob = MutableLiveData<ArrayList<String>>()
    val listJob: LiveData<ArrayList<String>> get() = _listJob

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun getListJob(url:String) {
        _isLoading.value = true

        val client = AsyncHttpClient()
//        val url = ApiConfig.getApiService().searchJob(position, company, location, notes, thirdparty)
        val url = "http://34.124.223.74/api/Job/Detail"
        val params = RequestParams()
        params.put("input", url)

        client.post(url, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                _isLoading.value = false

                val listJob = ArrayList<String>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result).getJSONObject("data")
                    val guid = jsonObject.getString("guid")
                    val kindofwork = jsonObject.getString("kindofwork")
                    val position = jsonObject.getString("position")
                    val company = jsonObject.getString("companyname")
                    val location = jsonObject.getString("location")
                    val education = jsonObject.getString("education")
                    val major = jsonObject.getString("major")
                    val description = jsonObject.getString("description")
                    val thirdparty = jsonObject.getString("thirdparty")
                    val notes = jsonObject.getString("notes")
                    val minimum = jsonObject.getString("minimumyears")
                    val skills = jsonObject.getString("skills")
                    val link = jsonObject.getString("link")
                    val image = jsonObject.getString("image")

                    listJob.add("$guid\n - $kindofwork-$position\n - $company\n - $location\n - $education\n -$major\n - $description\n - $minimum\n - $skills - $link - $notes\n  -$thirdparty\n-$image")

                    _listJob.value = listJob
                } catch (e: JSONException) {
                    _toastMessage.value = "Error parsing JSON"
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
                _isLoading.value = false
                if (error != null) {
                    _toastMessage.value = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                }
            }

        })


    }

    companion object {
        private const val TAG = "ListJobViewModel"
    }
}