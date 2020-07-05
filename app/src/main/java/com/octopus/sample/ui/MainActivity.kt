package com.octopus.sample.ui

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.octopus.sample.R

class MainActivity : BaseActivity() {

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
            Navigation.findNavController(this, R.id.navHostFragment).navigateUp()

    companion object {

        fun launch(activity: FragmentActivity) =
                activity.apply {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
    }
}