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
import zisis.aristofanis.animehouse.anime_list.presentation.adapters.AnimeListAdapterV2
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeListContractV2
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.AnimeListViewModelV2
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeListFragmentV2 : BaseFragment(R.layout.fragment_anime_list) {

    private val viewModel: AnimeListViewModelV2 by viewModels()


    private val adapter = AnimeListAdapterV2 { listItemClickIntentAction ->
        viewModel.consumeEvent(listItemClickIntentAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeListRecyclerView.layoutManager = LinearLayoutManager(activity)
        registerIntentActionListeners()
        viewModel.consumeEvent(
            AnimeListContractV2.AnimesEvent.GetAnimeListWithFilter(
                AnimeListUseCase.AnimeFilter()
            )
        )
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.state.collect { renderState(it) } }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, AnimeDetailsFragment(anime)).addToBackStack(null)
        }
    }

    private fun renderState(state: AnimeListContractV2.AnimesState) {
        renderEffects(state.effects)
        renderAnimes(state.animesStatus)
        renderLoading(state.loading)
    }

    private fun renderAnimes(animes: AnimeListContractV2.AnimesStatusV2) {
        when (animes) {
            is AnimeListContractV2.AnimesStatusV2.EmptyList -> Unit
            is AnimeListContractV2.AnimesStatusV2.DisplayAnimeList -> renderQueryResult(animes.animeList)
            //  is AnimeListContractV2.AnimeListStatus.ShowError -> renderError(animes.errorText)
        }
    }

    private fun renderEffects(effects: AnimeListContractV2.AnimesEffects) {
        effects.navigateToAnimeDetails?.let { navigateToAnimeDetails(it) }
        effects.errorText?.let { renderError(it) }
    }


    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
        viewModel.consumeEvent(AnimeListContractV2.AnimesEvent.ErrorShown)
    }

    private fun renderQueryResult(list: zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
