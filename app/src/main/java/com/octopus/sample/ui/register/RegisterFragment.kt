package com.octopus.sample.ui.register

import android.os.Bundle
import android.view.View
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.octopus.sample.R
import com.octopus.sample.http.Errors
import com.octopus.sample.utils.toast
import kotlinx.android.synthetic.main.fragment_register.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class RegisterFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_register

    private val mViewModel by instance<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mBtnSignIn.setOnClickListener {
            mViewModel.reg(tvUsername.text.toString(), tvPassword.text.toString(),
                    tvNick.text.toString(), tvNo.text.toString(),
                    if (rg.checkedRadioButtonId == R.id.rbStudent) "student" else "teacher")
        }

        observe(mViewModel.stateLiveData, this::onNewState)
    }

    private fun onNewState(state: RegisterViewState) {
        if (!state.msg.isNullOrEmpty()) {
            toast { state.msg }
        }

        mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.success) {
            activity?.finish()
        }
    }
}