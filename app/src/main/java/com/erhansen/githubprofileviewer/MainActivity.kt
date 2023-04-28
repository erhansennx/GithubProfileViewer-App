package com.erhansen.githubprofileviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
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

        with(activityMainBinding) {
            searchUser.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!searchUser.text.isNullOrEmpty()) {
                        GlobalScope.launch {
                            val result = userProfileApi.getUserInformation(searchUser.text.toString())
                            if (result.isSuccessful) {
                                println("Login : ${result.body()!!.login}")
                                println("avatar_url : ${result.body()!!.avatarUrl}")
                                println("name : ${result.body()!!.name}")
                                println("location : ${result.body()!!.location}")
                                println("followers : ${result.body()!!.followers}")
                                println("following : ${result.body()!!.following}")
                            } else if (result.code() == 404) {
                                // user not found!
                            }
                        }
                    }
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }
        }




    }



}