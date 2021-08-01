package zisis.aristofanis.animehouse.presentation.activities.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_anime_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeListContract
import zisis.aristofanis.animehouse.presentation.utils.visibilityExtension
import zisis.aristofanis.animehouse.presentation.view_models.AnimeListViewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeListFragment : BaseFragment(R.layout.fragment_anime_list) {

    private val viewModel: AnimeListViewModel by viewModels()


    private val adapter = AnimeListAdapter { listItemClickIntentAction ->
        viewModel.setIntentAction(listItemClickIntentAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeListRecyclerView.layoutManager = LinearLayoutManager(activity)
        registerIntentActionListeners()
        viewModel.setIntentAction(
            AnimeListContract.Event.LaunchAnimeListIntentAction(
                AnimeListUseCase.AnimeFilter()
            )
        )
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.eventSideEffects.collect { renderSideEffects(it) } }
        lifecycleScope.launch { viewModel.state.collect { renderState(it) } }
    }

    private fun renderSideEffects(sideEffect: AnimeListContract.ViewEffects) {
        when (sideEffect) {
            is AnimeListContract.ViewEffects.NavigateToAnimeDetails -> navigateToAnimeDetails(
                sideEffect.anime
            )
        }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, AnimeDetailsFragment(anime)).addToBackStack(null)
        }
    }

    private fun renderState(state: AnimeListContract.ViewState) {
        when (state.animeList) {
            is AnimeList.AnimeListItems -> renderQueryResult(state.animeList.animeList)
            is AnimeList.Loading -> renderLoading(state.animeList.loading)
            is AnimeList.ListError -> renderError(state.animeList.errorText)
        }
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderQueryResult(list: AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
