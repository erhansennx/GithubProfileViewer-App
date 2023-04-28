package com.erhansen.githubprofileviewer.service

import com.erhansen.githubprofileviewer.model.UserProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserProfileApi {

    @GET("/users/{username}")
    suspend fun getUserInformation(@Path("username") username: String): Response<UserProfileModel>

}