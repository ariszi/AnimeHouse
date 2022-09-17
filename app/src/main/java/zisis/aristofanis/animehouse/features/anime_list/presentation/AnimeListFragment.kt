package zisis.aristofanis.animehouse.features.anime_list.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.core.presentation.BaseFragment
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension
import zisis.aristofanis.animehouse.databinding.FragmentAnimeListBinding
import zisis.aristofanis.animehouse.features.anime_details.presentation.AnimeDetailsFragment
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.features.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.features.anime_list.domain.usecases.AnimeListUseCase

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeListFragment : BaseFragment(R.layout.fragment_anime_list) {

    private val viewModel: AnimeListViewModel by viewModels()
    private lateinit var loading: LottieAnimationView
    private lateinit var animeListView: RecyclerView


    private val adapter = AnimeListAdapter { listItemClickIntentAction ->
        viewModel.consumeEvent(listItemClickIntentAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAnimeListBinding.bind(view)
        bindViews(binding)
        registerIntentActionListeners()
        viewModel.consumeEvent(
            AnimeListContract.AnimesEvent.GetAnimeListWithFilter(
                AnimeListUseCase.AnimeFilter()
            )
        )
    }

    private fun bindViews(binding: FragmentAnimeListBinding) {
        loading = binding.loading
        animeListView = binding.animeListRecyclerView
        animeListView.layoutManager = LinearLayoutManager(activity)
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

    private fun renderQueryResult(list: AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListView.adapter = adapter
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
