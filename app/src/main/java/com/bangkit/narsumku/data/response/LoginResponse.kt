package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null
)

data class LoginResult (

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)