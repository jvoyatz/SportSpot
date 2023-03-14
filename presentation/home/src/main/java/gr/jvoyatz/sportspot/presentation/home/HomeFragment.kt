package gr.jvoyatz.sportspot.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.jvoyatz.core.common_android.adapters.setup
import gr.jvoyatz.sportspot.presentation.home.adapters.SportAdapter
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeBinding
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportCategory
import gr.jvoyatz.sportspot.presentation.home.models.LoadingHomeSportEvent
import gr.jvoyatz.sportspot.presentation.home.models.getErrorHomeSportCategory
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val sportsAdapter: SportAdapter by lazy {
        SportAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(binding, viewModel)
    }

    private fun init(binding: FragmentHomeBinding, viewModel: HomeViewModel) {
        binding.homeSportsRv.apply {
            sportsAdapter.apply {
                setHasFixedSize(true)
            }
            setup(sportsAdapter, LinearLayoutManager(this@HomeFragment.context))
        }
        initFlow(viewModel)
    }

    private fun initFlow(viewModel: HomeViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    submitAdapterData(it)
                }
            }
        }
    }

    private fun submitAdapterData(uiState: HomeUiState) {
        when {
            uiState.isLoading -> {
                sportsAdapter.submitList(listOf(LoadingHomeSportEvent))
            }
            uiState.isError -> {
                handleErrorUiState().also {
                    sportsAdapter.submitList(it)
                }
            }
            uiState.sportsEvents != null -> {
                handleSuccessUiState(uiState.sportsEvents)
                    .also {
                        sportsAdapter.submitList(it)
                    }
            }
            uiState.onFavoriteEventActionSuccess != null ->{
                handleOnFavoriteActionSuccess(uiState.onFavoriteEventActionSuccess)
            }
            else -> {
                viewModel.onUserIntent(HomeIntent.GetSportEvents)
            }
        }
    }

    private fun handleOnFavoriteActionSuccess(state: Boolean){
        state.let {
            it.let {
                if(it) getString(R.string.sport_event_favorite_success)
                else getString(R.string.sport_event_favorite_error)
            }.also {msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                viewModel.onUserIntent(HomeIntent.OnFavoriteActionConsumed)
            }
        }
    }
    private fun handleErrorUiState(): List<HomeSportCategory> {
        val errorMessage =
            getString(gr.jvoyatz.sportspot.core.common_android.R.string.sports_events_error)
        val errorModel = getErrorHomeSportCategory(errorMessage) {
            viewModel.onUserIntent(HomeIntent.GetSportEvents)
        }
        return listOf(errorModel)
    }

    private fun handleSuccessUiState(sportEvents: List<HomeSportCategory>): List<HomeSportCategory> {
//        return sportEvents.ifEmpty {
//            listOf(getEmptyHomeSportEvent())
//        }
        return sportEvents
    }
}