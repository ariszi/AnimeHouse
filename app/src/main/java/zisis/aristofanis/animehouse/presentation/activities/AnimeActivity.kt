package zisis.aristofanis.animehouse.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.presentation.activities.fragments.AnimeListFragment
import zisis.aristofanis.animehouse.presentation.state_management.AnimeContract
import zisis.aristofanis.animehouse.presentation.utils.InjectUtils
import zisis.aristofanis.animehouse.presentation.view_models.AnimeViewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AnimeActivity : BaseActivity(R.layout.activity_main) {

    private val factory = InjectUtils.provideAnimeViewModelFactory()
    private val viewModel: AnimeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerIntentActionListeners()
    }
    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.eventSideEffects.collect { renderSideEffects(it) } }
        lifecycleScope.launch { viewModel.state.collect { renderState(it) } }
    }
    private fun renderSideEffects(sideEffect: AnimeContract.ViewEffects) {
        when(sideEffect){
            is AnimeContract.ViewEffects.NavigateToAnimeList -> attachAnimeListFragment()
        }
    }

    private fun attachAnimeListFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<AnimeListFragment>(R.id.fragment_container)
        }
    }

    private fun renderState(state: AnimeContract.ViewState) {

    }
}
