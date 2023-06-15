package app.project.cualivy_capstone.recyclerview

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import app.project.cualivy_capstone.response.Detail


@Suppress("DEPRECATION")
class ListJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListJobBinding
    private val viewModel: ListJobViewModel by viewModels()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var jobAdapter: JobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager.getInstance(this)

        val layoutManager = LinearLayoutManager(this)
        binding.listJob.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listJob.addItemDecoration(itemDecoration)

        setupView()


//        jobAdapter = JobAdapter(ArrayList())
//        binding.listJob.adapter = jobAdapter
//
//        jobAdapter.setOnItemClickListener { guid ->
//
//            navigateToDetailJob(Detail("guid"))
//        }

        binding.listJob.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
            finish()
        }


        val base64Image = PreferenceManager.getBase64Image(this)
        if (base64Image != null) {
            getListJob(base64Image)
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

    private fun getListJob(base64Image: String) {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        viewModel.getListJob(base64Image)
        viewModel.listJob.observe(this) { listJob ->
            val adapter = JobAdapter(listJob)
            binding.listJob.adapter = adapter

        }
    }

//    private fun navigateToDetailJob(guid: Detail) {
//        preferenceManager.saveGuid(guid)
//        val intent = Intent(this, DetailActivity::class.java)
//        startActivity(intent)
//    }
    }

