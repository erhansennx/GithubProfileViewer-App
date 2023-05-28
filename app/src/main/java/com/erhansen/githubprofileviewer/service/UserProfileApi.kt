package com.erhansen.githubprofileviewer.service

import com.erhansen.githubprofileviewer.model.FollowersModel
import com.erhansen.githubprofileviewer.model.RepositoriesModel
import com.erhansen.githubprofileviewer.model.UserProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserProfileApi {

    @GET("/users/{username}")
    suspend fun getUserInformation(@Path("username") username: String): Response<UserProfileModel>
    @GET("/users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: String): Response<RepositoriesModel>
    @GET("/users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): Response<FollowersModel>
    @GET("/users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): Response<FollowersModel>

}