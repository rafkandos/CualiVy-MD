package app.project.cualivy_capstone.preview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import app.project.cualivy_capstone.main.MainActivity
import app.project.cualivy_capstone.databinding.ActivityCameraPreviewBinding
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.process.ProcessActivity
import java.io.ByteArrayOutputStream

class CameraPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraPreviewBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        binding.ivPreviewCamera.setImageBitmap(imageBitmap)

        binding.btnRescan.setOnClickListener { rescan() }
        binding.btnProcess.setOnClickListener {
            val imageData = imageBitmap?.let { getImageData(it) }
            val base64Image = imageData?.let { encodeToBase64(it) }

            // Simpan base64 string gambar ke SharedPreferences
            base64Image?.let { PreferenceManager.saveBase64Image(this, it) }
            startActivity(Intent(this, ProcessActivity::class.java))
        }

        setupView()
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
    private fun rescan() {
        // Open camera again to take picture
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainActivity.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val intent = Intent(this, CameraPreviewActivity::class.java)
            intent.putExtra("imageBitmap", imageBitmap)
            startActivity(intent)
            finish()

        }
    }

    private fun getImageData(imageBitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun encodeToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 200
    }
}