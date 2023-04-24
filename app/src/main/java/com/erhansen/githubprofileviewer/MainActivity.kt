package com.erhansen.githubprofileviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.erhansen.githubprofileviewer.databinding.ActivityMainBinding
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)
        GlobalScope.launch {
            val result = userProfileApi.getUserInformation()
            if (result != null) {
                println("Login : ${result.body()!!.login}")
                println("avatar_url : ${result.body()!!.avatarUrl}")
                println("name : ${result.body()!!.name}")
                println("location : ${result.body()!!.location}")
                println("followers : ${result.body()!!.followers}")
                println("following : ${result.body()!!.following}")
            }

        }


    }



}