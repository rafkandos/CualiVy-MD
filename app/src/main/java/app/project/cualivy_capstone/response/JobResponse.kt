package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName

data class JobResponse(
    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<DataItem>? = null
)

//data class ListJobResponse(
//
//)
