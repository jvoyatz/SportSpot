package gr.jvoyatz.sportspot.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import gr.jvoyatz.core.common_android.adapters.AdapterDiffCallback
import gr.jvoyatz.sportspot.presentation.home.adapters.viewholders.SportEventViewHolder
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemChildRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeFavorableSportEvent

class SportEventsAdapter :
    ListAdapter<HomeFavorableSportEvent, SportEventViewHolder>(FAVORABLE_SPORT_EVENT_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomeRvItemChildRvItemBinding.inflate(inflater, parent, false)

        return SportEventViewHolder(binding) { position ->
            getItem(position).also { fav ->
                fav.onFavoriteAction(fav.event, fav.isFavorite)
            }
        }
    }

    override fun onBindViewHolder(holder: SportEventViewHolder, position: Int) {
        (getItem(position) as HomeFavorableSportEvent).also {
            holder.bind(it)
        }
    }
//    override fun getItemId(position: Int): Long {
//        return getItem(position).event.id.hashCode().toLong()
//    }
}

private val FAVORABLE_SPORT_EVENT_DIFF_CALLBACK =
    object : AdapterDiffCallback<HomeFavorableSportEvent>() {
        override fun isSameItem(
            oldItem: HomeFavorableSportEvent,
            newItem: HomeFavorableSportEvent
        ) =
            oldItem.event.id == newItem.event.id

        override fun isContentSame(
            oldItem: HomeFavorableSportEvent,
            newItem: HomeFavorableSportEvent
        ) =
            oldItem == newItem
    }