package com.erhansen.githubprofileviewer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.UserProfileModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
object BottomDialogHelper {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var view: View
    private lateinit var userProfileLayout: LinearLayout
    private lateinit var context: Context
    private lateinit var loadProgress: ProgressBar
    private lateinit var profileImage: CircleImageView
    private lateinit var nameText: TextView
    private lateinit var usernameText: TextView
    private lateinit var biographyText: TextView
    private lateinit var followerText: TextView
    private lateinit var locationText: TextView
    private lateinit var emailText: TextView
    private lateinit var companyText: TextView
    private lateinit var twitterText: TextView
    private lateinit var linkText: TextView
    private lateinit var showRepositories: TextView
    private lateinit var showRepoProgress : ProgressBar

    fun bottomDialogInit(context: Context) {
        this.context = context
        bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        layoutInflater = LayoutInflater.from(context)
        view = layoutInflater.inflate(R.layout.bottom_sheet_profile, null)

        loadProgress = view.findViewById(R.id.loadProgress)
        userProfileLayout = view.findViewById(R.id.userProfileLayout)
        profileImage = view.findViewById(R.id.profilePhoto)
        nameText = view.findViewById(R.id.nameText)
        usernameText = view.findViewById(R.id.usernameText)
        biographyText = view.findViewById(R.id.biographyText)
        followerText = view.findViewById(R.id.followerText)
        locationText = view.findViewById(R.id.locationText)
        emailText = view.findViewById(R.id.emailText)
        companyText = view.findViewById(R.id.companyText)
        twitterText = view.findViewById(R.id.twitterText)
        linkText = view.findViewById(R.id.linkText)
        showRepositories = view.findViewById(R.id.showRepositories)
        showRepoProgress = view.findViewById(R.id.showRepoProgress)

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    fun showUserData(user: Response<UserProfileModel>) {
        nullViewCheck(user)
        loadProgress.visibility = View.GONE
        userProfileLayout.visibility = View.VISIBLE
        Glide.with(context).load(user.body()?.avatarUrl).centerCrop().placeholder(
            R.drawable.ic_launcher_background
        ).into(profileImage)
        nameText.text = user.body()?.name
        usernameText.text = user.body()!!.login
        biographyText.text = user.body()?.bio
        followerText.text = "${user.body()!!.followers} followers · ${user.body()!!.following} following"
        locationText.text = user.body()?.location
        emailText.text = user.body()?.email.toString()
        companyText.text = user.body()?.company.toString()
        twitterText.text = user.body()?.twitterUsername.toString()
        linkText.text = user.body()?.blog
        showRepoProgress.visibility = View.GONE
    }

    private fun nullViewCheck(result: Response<UserProfileModel>) {
        if (result.body()?.name.isNullOrEmpty()) nameText.visibility = android.view.View.GONE else nameText.visibility = android.view.View.VISIBLE
        if (result.body()?.name.isNullOrEmpty()) nameText.visibility = android.view.View.GONE else nameText.visibility = android.view.View.VISIBLE
        if (result.body()?.bio.isNullOrEmpty()) biographyText.visibility = android.view.View.GONE else biographyText.visibility = android.view.View.VISIBLE
        if (result.body()?.location.isNullOrEmpty()) locationText.visibility = android.view.View.GONE else locationText.visibility = android.view.View.VISIBLE
        if (result.body()?.email.toString() == "null") emailText.visibility = android.view.View.GONE else emailText.visibility = android.view.View.VISIBLE
        if (result.body()?.company.toString() == "null") companyText.visibility = android.view.View.GONE else companyText.visibility = android.view.View.VISIBLE
        if (result.body()?.twitterUsername.toString() == "null") twitterText.visibility = android.view.View.GONE else twitterText.visibility = android.view.View.VISIBLE
        if (result.body()?.blog.isNullOrEmpty()) linkText.visibility = android.view.View.GONE else linkText.visibility = android.view.View.VISIBLE
    }



}