package gr.jvoyatz.sportspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gr.jvoyatz.sportspot.core.common.AppDispatchers
import gr.jvoyatz.sportspot.databinding.ActivitySportSpotBinding
import javax.inject.Inject

@AndroidEntryPoint
class SportSportActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySportSpotBinding

    @Inject
    lateinit var dispatchers: AppDispatchers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySportSpotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_sport_spot)
    }
}