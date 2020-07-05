package com.octopus.sample.ui.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.octopus.sample.R
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.activity_add.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class AddActivity : BaseActivity() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId = R.layout.activity_add

    private val mViewModel by instance<CreateViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBtnCreate.setOnClickListener {
            mViewModel.addClass(tvClass.text.toString())
        }

        observe(mViewModel.stateLiveData) { state ->
            if (!state.msg.isNullOrEmpty()) {
                toast { state.msg }
            }

            mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        }
    }

}