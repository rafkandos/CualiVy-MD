package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Any,

    val isLogin: Boolean
)
