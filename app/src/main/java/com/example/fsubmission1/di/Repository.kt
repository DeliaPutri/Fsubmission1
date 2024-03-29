package com.example.fsubmission1.di

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import com.example.fsubmission1.remote.Response.DetailRespon
import com.example.fsubmission1.remote.Response.FResponse
import com.example.fsubmission1.remote.Response.ListUser
import com.example.fsubmission1.remote.Response.MachongResponse
import com.example.fsubmission1.remote.database.DaoUser
import com.example.fsubmission1.remote.database.FavoriteUser
import com.example.fsubmission1.retrofit.BaseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository private constructor(
    private val baseApi: BaseApi,
    private val daoUser: DaoUser
){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = daoUser.getAllFavorite()

    fun getFavUserByUsername(username: String): LiveData<FavoriteUser> =
        daoUser.getFavUserByUsername(username)
    fun insertUsers(gitUser: FavoriteUser) = executorService.execute {
        daoUser.insertUsers(gitUser)
    }
    fun deleteUsers(gitUser: FavoriteUser) = executorService.execute {
        daoUser.deleteUsers(gitUser)
    }

    fun getListUsers(query: String): LiveData<List<ListUser>> {
        _isLoading.value = true

        val listUsers = MutableLiveData<List<ListUser>>()
        val user = baseApi.getUsers(query)

        user.enqueue(object : Callback<MachongResponse> {
            override fun onResponse(
                call: Call<MachongResponse>,
                response: Response<MachongResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    listUsers.value = response.body()!!.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MachongResponse>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return listUsers
    }
    fun getDetailUser(username: String): LiveData<DetailRespon> {
        _isLoading.value = true

        val detailUser = MutableLiveData<DetailRespon>()
        val user = baseApi.getDetailUser(username)

        user.enqueue(object : Callback<DetailRespon> {
            override fun onResponse(
                call: Call<DetailRespon>,
                response: Response<DetailRespon>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailRespon>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return detailUser
    }
    fun getFollowerUser(username: String): LiveData<List<FResponse>> {
        _isLoading.value = true

        val follower = MutableLiveData<List<FResponse>>()
        val user = baseApi.getFollowerUser(username)

        user.enqueue(object : Callback<List<FResponse>> {
            override fun onResponse(
                call: Call<List<FResponse>>,
                response: Response<List<FResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    follower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FResponse>>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return follower
    }
    fun getFollowingUser(username: String): LiveData<List<FResponse>> {
        _isLoading.value = true

        val following = MutableLiveData<List<FResponse>>()
        val user = baseApi.getFollowingUser(username)

        user.enqueue(object : Callback<List<FResponse>> {
            override fun onResponse(
                call: Call<List<FResponse>>,
                response: Response<List<FResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FResponse>>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return following
    }
    companion object {
        private const val TAG = "MainViewModel"

        @Volatile
        var INSTANCE: Repository? = null
        fun getInstance(
            apiService: BaseApi,
            daoUser: DaoUser
        ): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(apiService,daoUser)
            }.also { INSTANCE = it }
    }

}