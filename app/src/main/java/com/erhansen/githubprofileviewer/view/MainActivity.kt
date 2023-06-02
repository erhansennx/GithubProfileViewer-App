package com.erhansen.githubprofileviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erhansen.githubprofileviewer.databinding.ActivityMainBinding
import com.erhansen.githubprofileviewer.fragments.SearchUserFragment
import com.erhansen.githubprofileviewer.utils.CreateFragment

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //val createFragment = CreateFragment(this@MainActivity, SearchUserFragment(), null)
        CreateFragment.create(this@MainActivity, SearchUserFragment(), null)

    }


}