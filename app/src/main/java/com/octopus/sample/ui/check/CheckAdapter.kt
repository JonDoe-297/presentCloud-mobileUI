package com.octopus.sample.ui.check

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.octopus.sample.R
import com.octopus.sample.entity.Check
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.ui.stu.StuActivity

/**
 * @Author： zy
 * @Date： 2020/7/4 9:07 PM
 * @Description：
 */
class DetailAdapter() : RecyclerView.Adapter<DetailViewHolder>() {

    private val mList = arrayListOf<Check>()

    val liveData = MutableLiveData<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount() = mList.size

    fun setList(list: List<Check>?) {
        mList.clear()
        if (!list.isNullOrEmpty())
            mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.binds(mList[position], liveData)
    }

}

class DetailViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvName = view.findViewById<TextView>(R.id.tvRepoName)
    private val tvState = view.findViewById<TextView>(R.id.tvRepoDesc)

    @SuppressLint("SetTextI18n")
    fun binds(data: Check, liveData: MutableLiveData<Int>) {
        tvName.text = "Num：${data.classnum}"
        tvState.text = data.starttime.split(".")[0] + "  到  " + data.endtime.split(".")[0]

        view.setOnClickListener {
            view.context.startActivity(Intent(view.context, StuActivity::class.java)
                    .putExtra(StuActivity.CLASS_NUM, data.classnum)
                    .putExtra(StuActivity.CHECK_ID, data.checkininfoid.toString()))
        }
    }

}