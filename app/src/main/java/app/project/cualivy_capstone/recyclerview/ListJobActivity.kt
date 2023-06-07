package app.project.cualivy_capstone.recyclerview

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.project.cualivy_capstone.adapter.JobAdapter
import app.project.cualivy_capstone.databinding.ActivityListJobBinding
import app.project.cualivy_capstone.detail.DetailActivity
import app.project.cualivy_capstone.preference.PreferenceManager
import java.util.*

@Suppress("DEPRECATION")
class ListJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListJobBinding
    private var base64Image: String? = null
    private val viewModel: ListJobViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

        val imageUri = intent.getParcelableExtra<Uri>("base64Image")

        if (imageUri != null) {
//            base64Image = convertUriToBase64(imageUri)
            getListJob("base64Image")
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listJob.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listJob.addItemDecoration(itemDecoration)

        setupView()

//        binding.listJob.setOnClickListener {
//            startActivity(Intent(this, DetailActivity::class.java))
//        }
//
//        base64Image?.let { getListJob(it) }
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

//    private fun convertUriToBase64(uri: Uri): String {
//        val inputStream = contentResolver.openInputStream(uri)
//        val byteArray = inputStream?.readBytes()
//        inputStream?.close()
//        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//    }

    private fun getListJob(base64Image: String) {
        viewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        val position = preferenceManager.getPosition()
        val company = preferenceManager.getCompany()
        val location = preferenceManager.getLocation()
        val notes = preferenceManager.getNotes()
        val thirdparty = preferenceManager.getThirdParty()

        viewModel.getListJob(position, company, location, notes, thirdparty)

        viewModel.listJob.observe(this, { listJob ->
            val adapter = JobAdapter(listJob as ArrayList<String>)
            binding.listJob.adapter = adapter
        })
    }

    companion object {
        private val TAG = ListJobActivity::class.java.simpleName
    }
}
