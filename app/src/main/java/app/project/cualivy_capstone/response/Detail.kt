package app.project.cualivy_capstone.response

import com.google.gson.annotations.SerializedName

data class Detail(

    val status: Int,
    val message: String,
    val data: JobData,
    val totalData: Int

)

data class JobData (
    val guid: String,
    val link: String
        )
