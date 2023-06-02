package com.erhansen.githubprofileviewer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.FollowersModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import com.erhansen.githubprofileviewer.utils.BottomDialogHelper
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowingAdapter(private val context: Context, private val followingList: FollowersModel) : RecyclerView.Adapter<FollowingAdapter.ItemHolder>() {

    private lateinit var githubUserAPI: UserProfileApi

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val followersProfileImage: CircleImageView =
            itemView.findViewById(R.id.followersProfileImage)
        val followingUserName: TextView = itemView.findViewById(R.id.followersUserNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_followers, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ItemHolder, position: Int) {
        Glide.with(context).load(followingList[position].avatarUrl).centerCrop()
            .placeholder(R.drawable.glide_loading).into(holder.followersProfileImage)
        holder.followingUserName.text = followingList[position].login
        holder.itemView.setOnClickListener {

            BottomDialogHelper.bottomDialogInit(context)
            githubUserAPI = UserProfileApiService.getInstance().create(UserProfileApi::class.java)

            CoroutineScope(Dispatchers.Main).launch {
                val userProfile = withContext(Dispatchers.IO) {
                    githubUserAPI.getUserInformation(followingList[position].login)
                }

                if (userProfile.isSuccessful) {
                    BottomDialogHelper.showUserData(userProfile)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return followingList.size
    }




}