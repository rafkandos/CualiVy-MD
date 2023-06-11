package app.project.cualivy_capstone.recyclerview

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.project.cualivy_capstone.api.ApiConfig
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.response.Detail
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ListJobViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _listJob = MutableLiveData<ArrayList<String>>()
    val listJob: LiveData<ArrayList<String>> get() = _listJob
//    private val _listJob = MutableLiveData<JSONArray>()
//    val listJob: LiveData<JSONArray> get() = _listJob



    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun getListJob(base64Image: String) {
        _isLoading.value = true

        val client = AsyncHttpClient()
//        val url = ApiConfig.getApiService().searchJob(position, company, location, notes, thirdparty)
        val url = "http://34.124.223.74/api/Job/SearchJob"
        val params = RequestParams()
        params.put("input", base64Image)

        client.post(url, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                _isLoading.value = false

                val listJob = ArrayList<String>()
                val listGuid = ArrayList<String>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONObject(result).getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val guid = jsonObject.getString("guid")
                        val position = jsonObject.getString("position")
                        val company = jsonObject.getString("companyname")
                        val location = jsonObject.getString("location")
//                        val notes = jsonObject.getString("notes")
//                        val thirdparty = jsonObject.getString("thirdparty")
                        val image = jsonObject.getString("image")
                        listJob.add("$guid\n $position\n $company\n $location\n")

//                        val originalString = listJob
//                        val newString = originalString.substring(36)

//                        listGuid.add(guid)
//                        listJob.add(jsonObject.toString())
                    }
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
