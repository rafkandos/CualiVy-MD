package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName

data class Detail(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Any,

    @field:SerializedName("totaldata")
    val totaldata: Int,

)
