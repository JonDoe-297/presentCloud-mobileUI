package com.octopus.sample.ui.main.home

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.architecture.core.image.GlideApp
import com.octopus.sample.R
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.utils.TimeConverter

class HomePagedAdapter : PagedListAdapter<ReceivedEvent, HomePagedViewHolder>(diffCallback) {

    private val itemEventObservable: MutableLiveData<String> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_received_event, parent, false)
        return HomePagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePagedViewHolder, position: Int) {
        holder.binds(getItem(position)!!, position, itemEventObservable)
    }

    fun observeItemEvent(): LiveData<String> {
        return itemEventObservable
    }

    companion object {

        private val diffCallback: DiffUtil.ItemCallback<ReceivedEvent> =
                object : DiffUtil.ItemCallback<ReceivedEvent>() {

                    override fun areItemsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean {
                        return oldItem.classid == newItem.classid
                    }

                    override fun areContentsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean {
                        return oldItem == newItem
                    }
                }
    }
}

class HomePagedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvEventContent: TextView = view.findViewById(R.id.tvEventContent)
    private val tvEventTime: TextView = view.findViewById(R.id.tvEventTime)

    fun binds(data: ReceivedEvent, position: Int, liveData: MutableLiveData<String>) {

        tvEventContent.text = data.classname

    }
}