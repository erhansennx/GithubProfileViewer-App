package com.erhansen.githubprofileviewer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.FollowersModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import com.erhansen.githubprofileviewer.utils.BottomDialogHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowersAdapter(private val context: Context, private val followersList: FollowersModel) : RecyclerView.Adapter<FollowersAdapter.ItemHolder>() {

    private lateinit var githubUserAPI: UserProfileApi

    class ItemHolder(itemView: View) : ViewHolder(itemView) {
        val followersProfileImage: CircleImageView = itemView.findViewById(R.id.followersProfileImage)
        val followingUserName: TextView = itemView.findViewById(R.id.followersUserNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_followers, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Glide.with(context).load(followersList[position].avatarUrl).centerCrop()
            .placeholder(R.drawable.glide_loading).into(holder.followersProfileImage)
        holder.followingUserName.text = followersList[position].login
        holder.itemView.setOnClickListener {

            BottomDialogHelper.bottomDialogInit(context)
            githubUserAPI = UserProfileApiService.getInstance().create(UserProfileApi::class.java)

            CoroutineScope(Dispatchers.Main).launch {
                val userProfile = withContext(Dispatchers.IO) {
                    githubUserAPI.getUserInformation(followersList[position].login)
                }

                if (userProfile.isSuccessful) {
                    BottomDialogHelper.showUserData(userProfile)
                }
            }

        }

    }





}