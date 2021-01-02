package zisis.aristofanis.animehouse.presentation.activities

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.utils.EMPTY
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListSideEffects
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListSideEffects.ShowAnimeState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState.ErrorState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState.LoadingState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState.ShowAnimeListState
import zisis.aristofanis.animehouse.presentation.utils.InjectUtils
import zisis.aristofanis.animehouse.presentation.utils.visibilityExtension
import zisis.aristofanis.animehouse.presentation.view_models.AnimeListViewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AnimeListActivity : BaseActivity(R.layout.activity_main) {

    private val factory = InjectUtils.provideAnimeListViewModelFactory()
    private val viewModel: AnimeListViewModel by viewModels { factory }

    private val adapter =
        AnimeListAdapter { listItemClickIntentAction ->
            viewModel.onIntentAction(listItemClickIntentAction)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeListRecyclerView.layoutManager = LinearLayoutManager(this)
        registerIntentActionListeners()
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.eventSideEffects.collect { renderSideEffects(it) } }
        lifecycleScope.launch { viewModel.currentStateFlow.collect { renderState(it) } }
    }

    private fun renderSideEffects(sideEffect: AnimeListSideEffects) {
        when (sideEffect) {
            is ShowAnimeState -> renderAnime(sideEffect.result)
        }
    }

    private fun renderState(state: AnimeListState) {
        when (state) {
            is ShowAnimeListState -> renderQueryResult(state.animeList)
            is LoadingState -> renderLoading(state)
            is ErrorState -> renderError(state.errorText)
        }
    }

    private fun renderAnime(anime: Anime) {
        Toast.makeText(this, "${anime.title.english}$EMPTY${anime.popularity}", LENGTH_SHORT).show()
    }

    private fun renderError(errorText: String) {
        Toast.makeText(this, errorText, LENGTH_LONG).show()
    }

    private fun renderQueryResult(list: AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: LoadingState) {
        loading.visibilityExtension(visibility.loading)
    }

}
