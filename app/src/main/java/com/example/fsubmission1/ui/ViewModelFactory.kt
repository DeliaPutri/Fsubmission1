package com.example.fsubmission1.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fsubmission1.di.Injection
import com.example.fsubmission1.di.Repository

class ViewModelFactory private constructor(
    private val repo: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepo(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }


}