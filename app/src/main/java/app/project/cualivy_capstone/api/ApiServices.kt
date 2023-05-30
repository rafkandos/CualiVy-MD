package app.project.cualivy_capstone.api


import app.project.cualivy_capstone.response.Detail
import app.project.cualivy_capstone.response.Login
import app.project.cualivy_capstone.response.Register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("guid") guid: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("token") token: String,
        @Field("createdat") createdat: String,
        @Field("updatedat") updatedat: String

    ): Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>

    @FormUrlEncoded
    @POST("detail")
    fun detail(
        @Field("guid") guid: String
    ): Call<Detail>

}