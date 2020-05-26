package me.brendonp.tabcorp.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_launch.view.*
import me.brendonp.tabcorp.R
import me.brendonp.tabcorp.presentation.models.BaseDisplayItem
import me.brendonp.tabcorp.presentation.models.HeaderDisplayItem
import me.brendonp.tabcorp.presentation.models.LaunchDisplayItemList
import java.lang.IllegalArgumentException

class LaunchListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val ITEM_TYPE_HEADER = 1
        const val ITEM_TYPE_LAUNCH = 2
    }

    private var data: List<BaseDisplayItem> = emptyList()
    private lateinit var onClick: (LaunchDisplayItemList) -> Unit

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_HEADER -> LaunchHeaderViewHolder(
                inflater.inflate(
                    R.layout.item_header,
                    parent,
                    false
                )
            )
            ITEM_TYPE_LAUNCH -> LaunchViewHolder(
                inflater.inflate(
                    R.layout.item_launch,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Unknown view type in adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val displayItem = data[position]
        when (holder.itemViewType) {
            ITEM_TYPE_HEADER -> bindHeader(
                holder as LaunchHeaderViewHolder,
                displayItem as HeaderDisplayItem
            )
            ITEM_TYPE_LAUNCH -> bindLaunch(
                holder as LaunchViewHolder,
                displayItem as LaunchDisplayItemList
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is HeaderDisplayItem -> ITEM_TYPE_HEADER
            is LaunchDisplayItemList -> ITEM_TYPE_LAUNCH
            else -> -1
        }
    }

    fun setData(data: List<BaseDisplayItem>, onClick: (LaunchDisplayItemList) -> Unit) {
        this.data = data
        this.onClick = onClick
        notifyDataSetChanged()
    }

    private fun bindHeader(viewHolder: LaunchHeaderViewHolder, data: HeaderDisplayItem) {
        viewHolder.header.text = data.title
    }

    private fun bindLaunch(viewHolder: LaunchViewHolder, data: LaunchDisplayItemList) {
        viewHolder.missionName.text = data.missionName
        viewHolder.rocketName.text = data.rocketName
        viewHolder.launchDate.text = data.launchDate
        viewHolder.setClickListener(View.OnClickListener { onClick(data) })
    }

    inner class LaunchHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val header = itemView as TextView
    }

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val missionName = itemView.missionName!!
        val rocketName = itemView.rocketName!!
        val launchDate = itemView.launchDate!!

        fun setClickListener(onClick: View.OnClickListener) {
            itemView.container.setOnClickListener(onClick)
        }
    }
}