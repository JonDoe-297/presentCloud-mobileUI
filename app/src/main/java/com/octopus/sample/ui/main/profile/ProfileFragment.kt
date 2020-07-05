package com.octopus.sample.ui.main.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.octopus.sample.R
import com.octopus.sample.manager.UserManager
import com.octopus.sample.ui.add.AddActivity
import com.octopus.sample.ui.create.CreateActivity
import com.octopus.sample.ui.login.LoginActivity
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    private val mViewModel by instance<ProfileViewModel>()

    override val layoutId: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        observe(mViewModel.viewStateLiveData, this::onNewState)

        mBtnEdit.setOnClickListener { toast { "coming soon..." } }

        mFabAdd.setOnClickListener {
            startActivity(Intent(activity,
                    if (UserManager.INSTANCE.roleList[0].roleName != "student") CreateActivity::class.java
                    else AddActivity::class.java))
        }

        mBtnSignOut.setOnClickListener {
            mViewModel.clearPrefsUser()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onNewState(state: ProfileViewState) {
        if (state.userInfo != null) {
            mTvNickname.text = "${state.userInfo.username}（${if (state.userInfo.roleList[0].roleName != "student") "老师" else "学生"}）"
            mTvBio.text = state.userInfo.useraddress
        }
    }
}