package gr.jvoyatz.sportspot.presentation.home.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import gr.jvoyatz.core.common_android.adapters.*
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants.TYPE_EMPTY
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants.TYPE_ERROR
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants.TYPE_ITEM
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants.TYPE_LOADING
import gr.jvoyatz.sportspot.core.common_android.R
import gr.jvoyatz.sportspot.core.common_android.databinding.ListItemBinding
import gr.jvoyatz.sportspot.core.common_android.databinding.ListItemErrorBinding
import gr.jvoyatz.sportspot.presentation.home.adapters.viewholders.SportViewHolder
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvLoadingItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvents
import timber.log.Timber


class SportAdapter : ListAdapter<HomeSportEvents, ViewHolder>(SPORT_DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.id) {
            AdapterItemTypeConstants.LOADING -> TYPE_LOADING
            AdapterItemTypeConstants.ITEM -> TYPE_ITEM
            AdapterItemTypeConstants.ERROR -> TYPE_ERROR
            AdapterItemTypeConstants.EMPTY -> TYPE_EMPTY
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("onCreateViewHolder() called with: parent = " + parent + ", viewType = " + viewType)
        return LayoutInflater.from(parent.context).let {

            when (viewType) {
                TYPE_LOADING -> {
                    val binding = FragmentHomeRvLoadingItemBinding.inflate(it, parent, false)
                    LoadingViewHolder(binding)
                }
                TYPE_EMPTY -> {
                    val binding = ListItemBinding.inflate(it, parent, false)
                    EmptyViewHolder(binding)
                }
                TYPE_ITEM -> {
                    val binding = FragmentHomeRvItemBinding.inflate(it, parent, false)
                    SportViewHolder(binding)
                }
                TYPE_ERROR -> {
                    ListItemErrorBinding.inflate(it, parent, false).run {
                        ErrorViewHolder(
                            this,
                            message = parent.context.getString(R.string.sports_events_error),
                        ) { position ->
                            with(getItem(position)!!) {
                                this.onErrorAction?.invoke()
                            }
                        }
                    }
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
            is EmptyViewHolder<*> -> {
                holder.bind {
                    (it as ListItemBinding).errorItem.text =
                        holder.itemView.context.getString(gr.jvoyatz.sportspot.core.common_android.R.string.sports_events_not_found)
                }
            }
            else -> {
                //do nothing
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