package app.project.cualivy_capstone.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import app.project.cualivy_capstone.ViewModelFactory
import app.project.cualivy_capstone.databinding.ActivityMainBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.preview.CameraPreviewActivity
import app.project.cualivy_capstone.preview.GalleryPreviewActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref : PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        binding.btnCamera.setOnClickListener {
            checkCameraPermission()
        }




        setupView()
        setupViewModel()
        loginCheck()
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

    private fun setupViewModel() {
        val pref = PreferenceManager.getInstance(this)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        ).get(MainViewModel::class.java)
    }

    private fun loginCheck() {

//        if (mainViewModel.getUser() != null) {
//            // User is logged in, proceed to MainActivity
//            // ...
//        } else {
//            // User is not logged in, redirect to LoginActivity
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val intent = Intent(this, CameraPreviewActivity::class.java)
            intent.putExtra("imageBitmap", imageBitmap)
            startActivity(intent)
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri = data?.data

            val intent = Intent(this, GalleryPreviewActivity::class.java)
            intent.putExtra("imageUri", imageUri)
            startActivity(intent)
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera()
//            } else {
//                Toast.makeText(
//                    this,
//                    "Camera permission denied. Cannot access camera.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
        const val CAMERA_REQUEST_CODE = 200
        private const val CAMERA_PERMISSION_REQUEST_CODE = 300
    }
}