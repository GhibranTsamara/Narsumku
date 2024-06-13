package com.bangkit.narsumku.data

import com.bangkit.narsumku.data.pref.UserModel
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.response.ErrorResponse
import com.bangkit.narsumku.data.response.LoginRequest
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.response.SignupRequest
import com.bangkit.narsumku.data.response.SignupResponse
import com.bangkit.narsumku.data.retrofit.ApiConfig
import com.bangkit.narsumku.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

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
            val response = SignupRequest(name, email, password)
            val client = apiService.register(response)
            if (client.error == true) {
                Results.Error(client.message ?: "Unknown error")
            } else {
                Results.Success(client)
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
            val response = LoginRequest(email, password)
            val client = apiService.login(response)

            if (client.error) {
                Results.Error(client.message)
            } else {
                val session = client.loginResult?.let {
                    UserModel(
                        email = email,
                        token = it.token,
                        isLogin = true
                    )
                }
                if (session != null) {
                    saveSession(session)
                }
                client.loginResult?.let { ApiConfig.updateToken(it.token) }
                Results.Success(client)
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