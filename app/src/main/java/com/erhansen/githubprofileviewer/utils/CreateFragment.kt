package com.erhansen.githubprofileviewer.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.RepositoriesModel

object CreateFragment {

    fun create(appCompatActivity: AppCompatActivity, targetFragment: Fragment, bundle: Bundle?) {
        val fragmentTransaction = appCompatActivity.supportFragmentManager.beginTransaction()
        if (bundle != null) targetFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentContainer, targetFragment, null).commit()
    }

}