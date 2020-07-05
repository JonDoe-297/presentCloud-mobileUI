package com.octopus.sample.ui.create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.octopus.sample.R
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.activity_create.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class CreateActivity : BaseActivity() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId = R.layout.activity_create

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

            if (state.classInfo != null) {
                tvClassId.text = "课程ID：${state.classInfo.classid}"
            }
        }
    }

}