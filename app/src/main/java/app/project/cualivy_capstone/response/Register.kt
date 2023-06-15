package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName


data class Register(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: String,

    @field:SerializedName("totaldata")
    val totaldata : Int
)
