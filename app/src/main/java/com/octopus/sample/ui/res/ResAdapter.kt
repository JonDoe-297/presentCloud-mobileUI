package com.octopus.sample.ui.res

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.octopus.sample.R
import com.octopus.sample.entity.Check
import com.octopus.sample.entity.Res
import com.octopus.sample.entity.UserInfo

/**
 * @Author： zy
 * @Date： 2020/7/4 9:07 PM
 * @Description：
 */
class DetailAdapter() : RecyclerView.Adapter<DetailViewHolder>() {

    private val mList = arrayListOf<Res>()

    val liveData = MutableLiveData<Res>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_received_event, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount() = mList.size

    fun setList(list: List<Res>?) {
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

    private val tvName = view.findViewById<TextView>(R.id.tvEventContent)

    @SuppressLint("SetTextI18n")
    fun binds(data: Res, liveData: MutableLiveData<Res>) {
        tvName.text = data.filepath + "（点击下载）"

        view.setOnClickListener {
            liveData.value = data
        }
    }

}