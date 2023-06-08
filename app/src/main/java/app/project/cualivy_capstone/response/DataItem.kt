package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName

data class DataItem(
    @field:SerializedName("guid")
    val guid: String? = null,

    @field:SerializedName("position")
    val position: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("notes")
    val notes: String? = null,

    @field:SerializedName("thirdparty")
    val thirdparty: String? = null,

    @field:SerializedName("image")
    val image: String? = null
)
