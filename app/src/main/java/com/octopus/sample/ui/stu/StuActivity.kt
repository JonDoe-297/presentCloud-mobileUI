package com.octopus.sample.ui.stu

import android.annotation.SuppressLint
import android.os.Bundle
import com.octopus.sample.R
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.activity_check.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class StuActivity : BaseActivity() {

    companion object {
        const val CLASS_NUM = "class_num"
        const val CHECK_ID = "check_id"
    }

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(checkKodeinModule)
    }

    override val layoutId = R.layout.activity_check

    private val mViewModel by instance<DetailViewModel>()

    private val mAdapter = DetailAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.title = "签到结果列表"
        mViewModel.getCheckinResult(intent.getStringExtra(CLASS_NUM) ?: "",
                intent.getStringExtra(CHECK_ID) ?: "")

        mSwipeRefreshLayout.isEnabled = false

        mRecyclerView.adapter = mAdapter

        observe(mViewModel.stateLiveData) { state ->
            if (!state.msg.isNullOrEmpty()) {
                toast { state.msg }
            }

            if (state.checkList != null) {
                mAdapter.setList(state.checkList)
            }
        }
    }

}