package com.erhansen.githubprofileviewer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.databinding.ActivityMainBinding
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.userProgress.visibility = View.GONE
        val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)
        val scope = CoroutineScope(Dispatchers.Main)

        with(activityMainBinding) {
            searchUser.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!searchUser.text.isNullOrEmpty()) {
                        userProgress.visibility = View.VISIBLE
                        searchUserLayout.boxBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.transparent)
                        CoroutineScope(Dispatchers.Main).launch {

                            val result = withContext(Dispatchers.IO) {
                                userProfileApi.getUserInformation(searchUser.text.toString())
                            }

                            if (result.isSuccessful) {
                                Glide.with(this@MainActivity).load(result.body()!!.avatarUrl).centerCrop().placeholder(R.drawable.ic_launcher_background).into(profilePhoto)
                                nameText.text = result.body()!!.name
                                usernameText.text = result.body()!!.login
                                biographyText.text = result.body()!!.bio
                                userProgress.visibility = View.GONE
                            } else if (result.code() == 404) {
                                // user not found!
                            }
                            userProgress.visibility = View.GONE
                            searchUserLayout.boxBackgroundColor = ContextCompat.getColor(this@MainActivity, R.color.white)
                        }
                        //runBlocking {//globalscope.launch {} exception :  Only the original thread that created a view hierarchy can touch its views. }
                    }
                    val imm = this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchUser.windowToken, 0)
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }
            scope.cancel()
        }




    }



}