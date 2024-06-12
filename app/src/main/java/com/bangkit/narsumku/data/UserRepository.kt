package com.bangkit.narsumku.data

import android.util.Log
import com.bangkit.narsumku.data.pref.UserModel
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.response.ErrorResponse
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.response.SignupResponse
import com.bangkit.narsumku.data.retrofit.ApiConfig
import com.bangkit.narsumku.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun signup(name: String, email: String, password: String): Results<SignupResponse> {
        Results.Loading
        return try {
            val response = apiService.register(name, email, password)
            if (response.error == true) {
                Results.Error(response.message ?: "Unknown error")
            } else {
                Results.Success(response)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        }
    }

    suspend fun login(email: String, password: String): Results<LoginResponse> {
        Results.Loading
        return try {
            val response = apiService.login(email, password)

            if (response.error) {
                Results.Error(response.message)
            } else {
                val session = response.loginResult?.let {
                    UserModel(
                        email = email,
                        token = it.token,
                        isLogin = true
                    )
                }
                if (session != null) {
                    saveSession(session)
                }
                response.loginResult?.let { ApiConfig.updateToken(it.token) }
                Results.Success(response)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}