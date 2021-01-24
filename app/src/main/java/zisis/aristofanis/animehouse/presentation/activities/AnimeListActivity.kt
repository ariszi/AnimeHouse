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
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.domain.utils.EMPTY
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.state_management.AnimeFilter
import zisis.aristofanis.animehouse.presentation.state_management.AnimeList
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract
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
            viewModel.setIntentAction(listItemClickIntentAction)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeListRecyclerView.layoutManager = LinearLayoutManager(this)
        registerIntentActionListeners()
        viewModel.setIntentAction(
            AnimeListContract.Event.LaunchAnimeListIntentAction(
                AnimeFilter(),
                AnimeListUseCase(AnimeListWithInfoRepository())
            )
        )
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.eventSideEffects.collect { renderSideEffects(it) } }
        lifecycleScope.launch { viewModel.state.collect { renderState(it) } }
    }

    private fun renderSideEffects(sideEffect: AnimeListContract.ViewEffects) {
        when (sideEffect) {
            is AnimeListContract.ViewEffects.ShowAnimeState -> renderAnime(sideEffect.result)
        }
    }

    private fun renderState(state: AnimeListContract.ViewState) {
        renderListState(state)

    }

    private fun renderListState(state: AnimeListContract.ViewState) {
        when (state.animeList) {
            is AnimeList.AnimeListItems -> renderQueryResult(state.animeList.animeList)
            is AnimeList.Loading -> renderLoading(state.animeList.loading)
            is AnimeList.ListError -> renderError(state.animeList.errorText)
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

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }

}
