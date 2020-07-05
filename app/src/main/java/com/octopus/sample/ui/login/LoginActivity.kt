package com.octopus.sample.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.octopus.sample.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction()
                    .add(R.id.flContainer, LoginFragment(), TAG)
                    .commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}