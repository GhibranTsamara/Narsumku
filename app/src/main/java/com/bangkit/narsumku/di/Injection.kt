package com.bangkit.narsumku.di

import android.content.Context
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}