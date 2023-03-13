package gr.jvoyatz.sportspot.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.jvoyatz.sportspot.presentation.home.adapters.SportAdapter
import gr.jvoyatz.sportspot.presentation.home.databinding.FragmentHomeBinding
import gr.jvoyatz.sportspot.presentation.home.models.LoadingHomeSportEvent
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

    private fun init(binding: FragmentHomeBinding, viewModel: HomeViewModel){
        binding.homeSportsRv.apply {
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
            this.adapter = sportsAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(10)
        }
        initFlow(viewModel)
    }

    private fun initFlow(viewModel: HomeViewModel){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{
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

            }
            uiState.sportsEvents != null -> {
                sportsAdapter.submitList(uiState.sportsEvents)
            }
            else -> {
                //sportsAdapter.submitList(listOf())
            }
        }
    }
}