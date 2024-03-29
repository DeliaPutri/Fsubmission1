package com.example.fsubmission1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fsubmission1.di.Repository
import com.example.fsubmission1.remote.Response.DetailRespon
import com.example.fsubmission1.remote.Response.FResponse
import com.example.fsubmission1.remote.database.FavoriteUser

class DetailViewModel (private val repository: Repository) : ViewModel(){
    fun getDetail(query: String): LiveData<DetailRespon> {
        return repository.getDetailUser(query)
    }

    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
    fun getFollower(username: String): LiveData<List<FResponse>> {
        return repository.getFollowerUser(username)
    }
    fun getFollowing(username: String): LiveData<List<FResponse>> {
        return repository.getFollowingUser(username)
    }
    fun getFavUserByUsername(username: String): LiveData<FavoriteUser> {
        return repository.getFavUserByUsername(username)
    }
    fun deleteUsers(gitUser: FavoriteUser) {
        return repository.deleteUsers(gitUser)
    }
    fun insertUsers(gitUser: FavoriteUser) {
        return repository.insertUsers(gitUser)
    }

}

