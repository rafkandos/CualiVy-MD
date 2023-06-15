package app.project.cualivy_capstone.api


import app.project.cualivy_capstone.response.Detail
import app.project.cualivy_capstone.response.JobResponse
import app.project.cualivy_capstone.response.Login
import app.project.cualivy_capstone.response.Register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("Auth/register")
    fun register(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String

    ): Call<Register>

    @FormUrlEncoded
    @POST("Auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>

    @FormUrlEncoded
    @POST("Job/Detail/{id}")
    fun detail(
        @Field("guid") guid: String
    ): Call<Detail>

    @FormUrlEncoded
    @POST("Job/SearchJob")
    fun searchJob(
        @Field("position") position : String,
        @Field("company") company : String,
        @Field("location") location : String,
        @Field("notes") notes : String,
        @Field("thirdparty") thirdparty : String,
        @Field("image") image : String
    ): Call<JobResponse>

}