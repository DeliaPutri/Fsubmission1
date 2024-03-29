package com.example.fsubmission1.remote.Response

import com.google.gson.annotations.SerializedName

data class FResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    )

