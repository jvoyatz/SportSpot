package gr.jvoyatz.sportspot.presentation.home.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.core.common_android.adapters.setup
import gr.jvoyatz.sportspot.presentation.home.R
import gr.jvoyatz.sportspot.presentation.home.adapters.SportEventsAdapter
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportCategory

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
            sportEventsRv.setup(eventsAdapter, LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false))
            sportExpandIcon.setImageResource(R.drawable.arrow_up)
        }
    }

    fun bind(sport: HomeSportCategory){
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