package com.bangkit.narsumku.data.retrofit

import com.bangkit.narsumku.data.response.LoginRequest
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.response.SignupRequest
import com.bangkit.narsumku.data.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body signupRequest: SignupRequest
    ): SignupResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}