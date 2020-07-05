package com.octopus.sample.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.octopus.sample.R
import com.octopus.sample.ui.MainActivity
import com.octopus.sample.ui.register.RegisterActivity
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


class LoginFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_login

    private val mViewModel by instance<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mBtnSignIn.setOnClickListener {
            mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
        }
        mBtnRegister.setOnClickListener {
            startActivity(Intent(activity, RegisterActivity::class.java))
        }

        observe(mViewModel.stateLiveData, this::onNewState)
    }

    private fun onNewState(state: LoginViewState) {
        if (!state.msg.isNullOrEmpty()) {
            toast { state.msg }
        }

        mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.autoLoginEvent != null                // has auto login info
                && state.autoLoginEvent.autoLogin       // allow auto login by user
                && state.useAutoLoginEvent              // ensure auto login info be used one time
        ) {
            tvUsername.setText(state.autoLoginEvent.username, TextView.BufferType.EDITABLE)
            tvPassword.setText(state.autoLoginEvent.password, TextView.BufferType.EDITABLE)

            mViewModel.onAutoLoginEventUsed()
            mViewModel.login(state.autoLoginEvent.username, state.autoLoginEvent.password)
        }

        if (state.loginInfo != null) {
            MainActivity.launch(activity!!)
        }

        requestPermission()
    }

    private fun requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    111)
        }
    }
}