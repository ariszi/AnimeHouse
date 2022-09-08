package zisis.aristofanis.animehouse.anime_list.presentation.activities.fragments

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
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.anime_list.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeListContract
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeListStatus
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.AnimeListViewModel
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension

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
            AnimeListContract.Event.GetAnimeListWithFilter(
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
        when (state.animeListStatus) {
            is AnimeListStatus.DisplayAnimeList -> renderQueryResult(state.animeListStatus.animeList)
            is AnimeListStatus.Loading -> renderLoading(state.animeListStatus.loading)
            is AnimeListStatus.ShowError -> renderError(state.animeListStatus.errorText)
        }
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderQueryResult(list: zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
