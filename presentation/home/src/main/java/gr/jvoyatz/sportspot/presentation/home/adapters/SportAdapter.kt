package gr.jvoyatz.sportspot.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import gr.jvoyatz.core.common_android.adapters.AdapterDiffCallback
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants
import gr.jvoyatz.core.common_android.adapters.EmptyViewHolder
import gr.jvoyatz.sportspot.presentation.home.adapters.viewholders.SportViewHolder
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvLoadingItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvents

private const val TYPE_LOADING = 1
private const val TYPE_ITEM = 2
private const val TYPE_ERROR = -1

class SportAdapter : ListAdapter<HomeSportEvents, ViewHolder>(SPORT_DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return when (item.id) {
            AdapterItemTypeConstants.LOADING -> TYPE_LOADING
            AdapterItemTypeConstants.ITEM -> TYPE_ITEM
            AdapterItemTypeConstants.ERROR -> TYPE_ERROR
            else -> TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let {

            when (viewType) {
                TYPE_LOADING -> {
                    val binding = FragmentHomeRvLoadingItemBinding.inflate(it, parent, false)
                    EmptyViewHolder(binding)
                }
                TYPE_ERROR -> {
                    val binding = FragmentHomeRvItemBinding.inflate(it, parent, false)
                    SportViewHolder(binding)
                }
                TYPE_ITEM -> {
                    val binding = FragmentHomeRvItemBinding.inflate(it, parent, false)
                    SportViewHolder(binding)
                }
                else -> {
                    val binding = FragmentHomeRvItemBinding.inflate(it, parent, false)
                    SportViewHolder(binding)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SportViewHolder -> {
                (getItem(position) as HomeSportEvents).also {
                    holder.bind(it)
                }
            }
            else -> { //do nothing
            }
        }
    }
}

private val SPORT_DIFF_CALLBACK = object : AdapterDiffCallback<HomeSportEvents>() {
    override fun isSameItem(oldItem: HomeSportEvents, newItem: HomeSportEvents) =
        oldItem.id == newItem.id

    override fun isContentSame(oldItem: HomeSportEvents, newItem: HomeSportEvents) =
        oldItem == newItem
}