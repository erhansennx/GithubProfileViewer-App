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
import com.erhansen.githubprofileviewer.model.UserProfileModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import kotlinx.coroutines.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.userProgress.visibility = View.GONE
        activityMainBinding.userProfileLayout.visibility = View.GONE
        activityMainBinding.userNotFound.visibility = View.GONE
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
                                userProfileLayout.visibility = View.VISIBLE
                                userNotFound.visibility = View.GONE
                                nullViewCheck(result)
                                Glide.with(this@MainActivity).load(result.body()!!.avatarUrl).centerCrop().placeholder(R.drawable.ic_launcher_background).into(profilePhoto)
                                nameText.text = result.body()!!.name
                                usernameText.text = result.body()!!.login
                                biographyText.text = result.body()?.bio
                                followerText.text = "${result.body()!!.followers} followers · ${result.body()!!.following} following"
                                locationText.text = result.body()?.location
                                emailText.text = result.body()?.email.toString()
                                companyText.text = result.body()?.company.toString()
                                twitterText.text = result.body()?.twitterUsername.toString()
                                linkText.text = result.body()?.blog
                                userProgress.visibility = View.GONE
                            } else if (result.code() == 404) {
                                // user not found!
                                userProfileLayout.visibility = View.GONE
                                userNotFound.visibility = View.VISIBLE
                                userNotFoundText.text = "${getString(R.string.user_not_found)} '${searchUser.text}'"
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

    private fun nullViewCheck(result: Response<UserProfileModel>) {
        with(activityMainBinding) {
            if (result.body()?.name.isNullOrEmpty()) nameText.visibility = View.GONE else nameText.visibility = View.VISIBLE
            if (result.body()?.name.isNullOrEmpty()) nameText.visibility = View.GONE else nameText.visibility = View.VISIBLE
            if (result.body()?.bio.isNullOrEmpty()) biographyText.visibility = View.GONE else biographyText.visibility = View.VISIBLE
            if (result.body()?.location.isNullOrEmpty()) locationText.visibility = View.GONE else locationText.visibility = View.VISIBLE
            if (result.body()?.email.toString() == "null") emailText.visibility = View.GONE else emailText.visibility = View.VISIBLE
            if (result.body()?.company.toString() == "null") companyText.visibility = View.GONE else companyText.visibility = View.VISIBLE
            if (result.body()?.twitterUsername.toString() == "null") twitterText.visibility = View.GONE else twitterText.visibility = View.VISIBLE
            if (result.body()?.blog.isNullOrEmpty()) linkText.visibility = View.GONE else linkText.visibility = View.VISIBLE
        }
    }



}