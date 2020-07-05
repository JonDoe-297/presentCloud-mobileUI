package com.octopus.sample.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.octopus.sample.R
import com.octopus.sample.entity.UserInfo

/**
 * @Author： zy
 * @Date： 2020/7/4 9:07 PM
 * @Description：
 */
class DetailAdapter() : RecyclerView.Adapter<DetailViewHolder>() {

    private val mList = arrayListOf<UserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount() = mList.size

    fun setList(list: List<UserInfo>?) {
        mList.clear()
        if (!list.isNullOrEmpty())
            mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.binds(mList[position])
    }


}

class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvName = view.findViewById<TextView>(R.id.tvRepoName)
    private val tvState = view.findViewById<TextView>(R.id.tvRepoDesc)

    fun binds(data: UserInfo) {
        tvName.text = data.username
    }

}