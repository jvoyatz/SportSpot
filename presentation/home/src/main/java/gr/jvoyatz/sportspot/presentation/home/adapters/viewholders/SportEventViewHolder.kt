@file:Suppress("unused")

package gr.jvoyatz.sportspot.presentation.home.adapters.viewholders

import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.sportspot.core.common_android.R
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeRvItemChildRvItemBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeFavorableSportEvent
import java.util.concurrent.TimeUnit


private const val SECOND_IN_MILLIS = 1000L
private const val DAY_HOURS = 24
private const val HOUR_MINUTES = 60
private const val MINUTE_SECONDS = 60

/**
 * Viewholder for the row which contains the events scheduled for the given Sport name
 */
class SportEventViewHolder(
    private val binding: FragmentHomeRvItemChildRvItemBinding,
    onClickPositionHandler: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var countDownTimer: CountDownTimer

    init {
        binding.eventFavorite.setOnClickListener {
            onClickPositionHandler(bindingAdapterPosition)
        }
    }

    fun bind(sport: HomeFavorableSportEvent) {
        if (this::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        binding.apply {
            binding.eventTime.text = sport.event.startDateTimeStamp.toString()
            binding.eventTeamsFirst.text = sport.event.team1
            binding.eventTeamsSecond.text = sport.event.team2
            val icon: Int =
                if (sport.isFavorite) gr.jvoyatz.sportspot.presentation.home.R.drawable.sport_favorite else gr.jvoyatz.sportspot.presentation.home.R.drawable.sport_non_favorite
            val diffUntilStartDate =
                sport.event.startDateTimeStamp * 1000 - System.currentTimeMillis()
                    .also {
                        if (it > 0) {
                            binding.eventFavorite.isEnabled = true
                            binding.eventFavorite.visibility = View.VISIBLE
                        } else {
                            binding.eventFavorite.isEnabled = false
                            binding.eventFavorite.visibility = View.INVISIBLE
                        }
                        binding.eventFavorite.setImageResource(icon)
                    }

            countDownTimer = object : CountDownTimer(diffUntilStartDate, SECOND_IN_MILLIS) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds: Long = millisUntilFinished / 1000
                    binding.eventTime.text = getCountDownString(seconds)
                }

                override fun onFinish() {
                    binding.eventTime.text = binding.root.context.getString(R.string.days)
                }
            }.start()
        }
    }

    private fun getCountDownString(seconds: Long): String {
        //seconds to days
        val days = TimeUnit.SECONDS.toDays(seconds)

        //get the total hours till then - the count of days * 24
        val hours = TimeUnit.SECONDS.toHours(seconds) - (days * DAY_HOURS)

        //get the total minutes till then - total hours * 60
        val minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60)

        //get the total seconds till then - total Minutes * 60
        val secondsLeft =
            TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60)

        var template = "%02d:%02d:%02d"
        if (days > 0) {
            val daysLabel = itemView.context.getString(R.string.days)
            template = "$days $daysLabel $template"
        }
        return template.format(hours, minutes, secondsLeft)
    }
}