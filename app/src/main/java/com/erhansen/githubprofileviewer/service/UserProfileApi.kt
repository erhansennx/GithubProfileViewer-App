package com.erhansen.githubprofileviewer.service

import com.erhansen.githubprofileviewer.model.UserProfileModel
import retrofit2.Response
import retrofit2.http.GET

interface UserProfileApi {

    @GET("/users/erhansennx")
    suspend fun getUserInformation(): Response<UserProfileModel>

}