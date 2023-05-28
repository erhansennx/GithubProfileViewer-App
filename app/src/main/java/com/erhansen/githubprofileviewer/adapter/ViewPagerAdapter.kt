package com.erhansen.githubprofileviewer.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erhansen.githubprofileviewer.fragments.FollowersFragment
import com.erhansen.githubprofileviewer.fragments.FollowingFragment
import com.erhansen.githubprofileviewer.fragments.RepositoriesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            2 -> RepositoriesFragment()
            else -> FollowersFragment()
        }
    }

}