package app.project.cualivy_capstone.preview

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import app.project.cualivy_capstone.MainActivity
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.databinding.ActivityGalleryPreviewBinding

class GalleryPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryPreviewBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        binding.ivPreviewGallery.setImageURI(imageUri)

        setupView()

        binding.btnGalleryAgain.setOnClickListener { galleryAgain() }
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

    @Suppress("DEPRECATION")
    private fun galleryAgain() {
        // Open gallery again to choose image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)

    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri = data?.data

            val intent = Intent(this, GalleryPreviewActivity::class.java)
            intent.putExtra("imageUri", imageUri)
            startActivity(intent)
            finish()
        }
    }


    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }
}