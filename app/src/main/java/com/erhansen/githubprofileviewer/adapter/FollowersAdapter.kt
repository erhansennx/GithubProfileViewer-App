package com.erhansen.githubprofileviewer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.FollowersModel
import de.hdodenhof.circleimageview.CircleImageView

class FollowersAdapter(private val context: Context, private val followersList: FollowersModel) : RecyclerView.Adapter<FollowersAdapter.ItemHolder>() {


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
            .placeholder(R.drawable.ic_launcher_background).into(holder.followersProfileImage)
        holder.followingUserName.text = followersList[position].login

    }


}