package app.project.cualivy_capstone.preview

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ByteArrayOutputStream

class GalleryViewModel : ViewModel() {

    val base64Image: MutableLiveData<String> = MutableLiveData()

    fun convertImageToBase64(bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        base64Image.value = base64
    }
}
