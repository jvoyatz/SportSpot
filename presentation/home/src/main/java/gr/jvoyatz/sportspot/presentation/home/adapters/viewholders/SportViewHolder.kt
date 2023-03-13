package gr.jvoyatz.sportspot.presentation.home.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.sportspot.presentation.home.R
import gr.jvoyatz.sportspot.presentation.home.adapters.SportEventsAdapter
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvents

/**
 * Viewholder for the row which contains the events scheduled for the given Sport name
 */
class SportViewHolder(
    private val binding: FragmentHomeRvItemBinding
): RecyclerView.ViewHolder(binding.root) {

    private val eventsAdapter: SportEventsAdapter by lazy {
        SportEventsAdapter()
    }

    init {
        binding.apply {
            sportExpandIcon.setOnClickListener{ onHandleItemExpansion() }
            sportEventsRv.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            eventsAdapter.setHasStableIds(true)
            sportEventsRv.adapter = eventsAdapter
            sportEventsRv.setHasFixedSize(true)
            sportEventsRv.setItemViewCacheSize(20)
            sportExpandIcon.setImageResource(R.drawable.arrow_up)
        }
    }

    fun bind(sport: HomeSportEvents){
        binding.apply {
            sportName.text = sport.name
            if(sport.iconId != -1) sportIcon.setImageResource(sport.iconId)
            eventsAdapter.submitList(sport.events)
        }
    }

    private fun onHandleItemExpansion(){
        binding.sportExpandIcon.isEnabled = false
        binding.sportEventsRv.apply {
            if(visibility == View.VISIBLE){
                visibility = View.GONE
                binding.sportExpandIcon.setImageResource(R.drawable.arrow_down)
            }else{
                visibility = View.VISIBLE
                binding.sportExpandIcon.setImageResource(R.drawable.arrow_up)
            }
        }
        binding.sportExpandIcon.isEnabled = true
    }
}