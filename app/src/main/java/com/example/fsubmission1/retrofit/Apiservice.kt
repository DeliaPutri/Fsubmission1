package com.example.fsubmission1.retrofit

import com.example.fsubmission1.remote.Response.DetailRespon
import com.example.fsubmission1.remote.Response.FResponse
import com.example.fsubmission1.remote.Response.MachongResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class Apiservice {
    companion object{
        fun getApiService(): BaseApi {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(BaseApi::class.java)
        }
    }
}
interface BaseApi{
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<MachongResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailRespon>

    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ): Call<List<FResponse>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<FResponse>>
}