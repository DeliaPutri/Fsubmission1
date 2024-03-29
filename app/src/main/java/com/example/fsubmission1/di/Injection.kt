package com.example.fsubmission1.di

import android.content.Context
import com.example.fsubmission1.remote.database.FavoriteUserDatabase
import com.example.fsubmission1.retrofit.Apiservice

object Injection {
    fun provideRepo(context: Context): Repository {
        val apiService = Apiservice.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val daoUser = database.gitUserDao()
        return Repository.getInstance(apiService,daoUser)
    }
}