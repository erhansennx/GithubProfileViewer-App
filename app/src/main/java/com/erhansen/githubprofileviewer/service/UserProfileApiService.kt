package com.erhansen.githubprofileviewer.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserProfileApiService {

    private const val baseURL = "https://api.github.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}