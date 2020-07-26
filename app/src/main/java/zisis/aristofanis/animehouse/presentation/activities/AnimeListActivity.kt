package zisis.aristofanis.animehouse.presentation.activities

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.presentation.activities.view_state.LoadingState
import zisis.aristofanis.animehouse.presentation.activities.view_state.ResultState
import zisis.aristofanis.animehouse.presentation.activities.view_state.ViewState
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.utils.InjectUtils
import zisis.aristofanis.animehouse.presentation.view_models.AnimeListViewModel

class AnimeListActivity : BaseActivity() {

    private val adapter = AnimeListAdapter { anime: Anime -> handleAction(anime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animeListRecyclerView.layoutManager = LinearLayoutManager(this)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        val factory = InjectUtils.provideAnimeListViewModelFactory()
        val viewModel: AnimeListViewModel by viewModels { factory }
        viewModel.uiModel.observe(this, Observer<ViewState> { viewState -> handleState(viewState) })
    }

    private fun handleAction(anime: Anime) {
        Toast.makeText(this, anime.title.english, LENGTH_LONG).show()
    }

    private fun handleState(state: ViewState) {
        when (state) {
            is LoadingState -> handleLoading(state.loadingVisibility())
            is ResultState<*> -> handleAnimeList(state.result as AnimeListWithInfo)
        }

    }

    private fun handleAnimeList(list: AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun handleLoading(visibility: Int) {
        loading.visibility = visibility
    }

}
