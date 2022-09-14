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
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.AnimeListViewModel
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeListFragment : BaseFragment(R.layout.fragment_anime_list) {

    private val viewModel: AnimeListViewModel by viewModels()


    private val adapter = AnimeListAdapter { listItemClickIntentAction ->
        viewModel.consumeEvent(listItemClickIntentAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeListRecyclerView.layoutManager = LinearLayoutManager(activity)
        registerIntentActionListeners()
        viewModel.consumeEvent(
            AnimeListContract.AnimesEvent.GetAnimeListWithFilter(
                AnimeListUseCase.AnimeFilter()
            )
        )
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, AnimeDetailsFragment(anime)).addToBackStack(null)
        }
    }

    private fun applyState(state: AnimeListContract.AnimesState) {
        state.animesNavigation?.let { navigate(it) }
        showAnimes(state.animesStatus)
        renderLoading(state.loading)
    }

    private fun showAnimes(animes: AnimeListContract.AnimesStatus) {
        when (animes) {
            is AnimeListContract.AnimesStatus.EmptyList -> Unit
            is AnimeListContract.AnimesStatus.DisplayAnimeList -> renderQueryResult(animes.animeList)
            is AnimeListContract.AnimesStatus.ShowError -> renderError(animes.errorText)
        }
    }

    private fun navigate(nav: AnimeListContract.AnimesNavigation) {
        when (nav) {
            is AnimeListContract.AnimesNavigation.NavigateBack -> navigateBack()
            is AnimeListContract.AnimesNavigation.NavigateToAnimeDetails ->
                navigateToAnimeDetails(nav.anime)
        }.also {
            viewModel.consumeEvent(AnimeListContract.AnimesEvent.AcknowledgeNavigation)
        }
    }

    private fun navigateBack() {
        TODO("Not yet implemented")
    }


    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
        viewModel.consumeEvent(AnimeListContract.AnimesEvent.ErrorShown)
    }

    private fun renderQueryResult(list: zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
