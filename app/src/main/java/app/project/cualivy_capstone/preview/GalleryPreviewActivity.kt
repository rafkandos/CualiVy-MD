package app.project.cualivy_capstone.preview

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.util.Base64
import app.project.cualivy_capstone.databinding.ActivityGalleryPreviewBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.process.ProcessActivity
import java.io.ByteArrayOutputStream

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
        binding.btnProcess.setOnClickListener {
            val imageData = imageUri?.let { it1 -> getImageData(it1) }
            val base64Image = imageData?.let { encodeImageToBase64(it) }

            // Simpan base64 string gambar ke SharedPreferences
            PreferenceManager.saveBase64Image(this, base64Image ?: "")
            startActivity(Intent(this, ProcessActivity::class.java))
        }
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
            val imageUri = data?.data as Uri
            binding.ivPreviewGallery.setImageURI(imageUri)

//            val intent = Intent(this, GalleryPreviewActivity::class.java)
//            intent.putExtra("imageUri", imageUri)
//            startActivity(intent)
//            finish()
        }
    }

    private fun getImageData(imageUri: Uri): ByteArray? {
        val inputStream = imageUri.let { contentResolver.openInputStream(it) }
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int
        if (inputStream != null) {
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        }
        return byteArrayOutputStream.toByteArray()
    }

    private fun encodeImageToBase64(imageData: ByteArray): String {
        return Base64.encodeToString(imageData, Base64.DEFAULT)
    }

    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }
}
