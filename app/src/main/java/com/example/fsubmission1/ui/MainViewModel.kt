package com.example.fsubmission1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fsubmission1.di.Repository
import com.example.fsubmission1.remote.Response.ListUser

class MainViewModel (private val repository: Repository) : ViewModel() {
    fun getAllUsers(query: String): LiveData<List<ListUser>> {
        return repository.getListUsers(query)
    }

    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}
