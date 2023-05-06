package com.erhansen.githubprofileviewer.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.RepositoriesModel

class CreateFragment(appCompatActivity: AppCompatActivity, targetFragment: Fragment, bundle: Bundle?) {

    init {
        val fragmentTransaction = appCompatActivity.supportFragmentManager.beginTransaction()
        if (bundle != null) targetFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentContainer, targetFragment, null).addToBackStack(null).commit()
    }

}