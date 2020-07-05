package com.octopus.sample.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bigkoo.pickerview.TimePickerView
import com.octopus.sample.R
import com.octopus.sample.manager.UserManager
import com.octopus.sample.ui.check.CheckActivity
import com.octopus.sample.ui.res.ResActivity
import com.octopus.sample.utils.toast
import com.qingmei2.architecture.core.base.view.activity.BaseActivity
import com.qingmei2.architecture.core.ext.observe
import kotlinx.android.synthetic.main.activity_detail.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : BaseActivity() {

    companion object {
        const val CLASS_NUM = "class_num"
    }

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(detailKodeinModule)
    }

    override val layoutId = R.layout.activity_detail

    private val mViewModel by instance<DetailViewModel>()

    private lateinit var mPvTime: TimePickerView

    private var mStartTime: String = ""
    private var mEndTime: String = ""

    private val mAdapter = DetailAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getClassDetail(intent.getStringExtra(CLASS_NUM) ?: "")

        mSwipeRefreshLayout.isEnabled = false

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        val selectedDate: Calendar = Calendar.getInstance()
        val startDate: Calendar = Calendar.getInstance()
        startDate.set(2020, 0, 23)
        val endDate: Calendar = Calendar.getInstance()
        endDate.set(2029, 11, 28)
        //时间选择器
        //时间选择器
        mPvTime = TimePickerView.Builder(this, TimePickerView.OnTimeSelectListener { date, v ->
            //选中事件回调
            // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

            if (mStartTime.isEmpty()) {
                mStartTime = getTimes(date)
            } else {
                mEndTime = getTimes(date)
                mViewModel.addCheckinInfo(mStartTime, mEndTime)
                mStartTime = ""
                mEndTime = ""
            }
        }) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(booleanArrayOf(true, true, true, true, true, true))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build()


        mRecyclerView.adapter = mAdapter

        if (UserManager.INSTANCE.roleList[0].roleName != "student") {
            mBtn.text = "选择时间并创建签到"
            mBtn.setOnClickListener {
                if (mStartTime.isEmpty()) toast { "选择开始时间" }
                else toast { "选择结束时间" }
                mPvTime.show()
            }
        } else {
            mBtn.text = "签到（已签忽略）"
            mBtn.setOnClickListener {
                mViewModel.checkin()
            }
            mBtnResUpload.visibility = View.GONE
        }

        mBtnCheck.setOnClickListener {
            startActivity(Intent(this, CheckActivity::class.java).putExtra(CLASS_NUM, intent.getStringExtra(CLASS_NUM)
                    ?: ""))
        }

        mBtnResList.setOnClickListener {
            startActivity(Intent(this, ResActivity::class.java))
        }

        mBtnResUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            this.startActivityForResult(intent, 100)
        }

        observe(mViewModel.stateLiveData) { state ->
            if (!state.msg.isNullOrEmpty()) {
                toast { state.msg }
            }

            if (state.classInfo != null) {
                mTvInfo.text = "课程名称：${state.classInfo.classX.classname}\n课程老师：${state.classInfo.teacher.username}"

                mAdapter.setList(state.classInfo.classX.studentList)
            }
        }
    }

    private fun getTimes(date: Date): String { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return format.format(date)
    }

    // 获取文件的真实路径
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return
        }
        val uri: Uri? = data.data // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
        val resolver = this.contentResolver
        val cursor: Cursor? = uri?.let { resolver.query(it, null, null, null, null) }
        if (cursor == null) {
            // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            val path: String? = uri?.path
            path?.let { mViewModel.upload(it) }
            return
        }
        if (cursor.moveToFirst()) {
            // 多媒体文件，从数据库中获取文件的真实路径
            val path: String = cursor.getString(cursor.getColumnIndex("_data"))
            mViewModel.upload(path)
        }
        cursor.close()
    }

}