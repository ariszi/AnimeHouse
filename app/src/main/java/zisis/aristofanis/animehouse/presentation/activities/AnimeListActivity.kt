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
import zisis.aristofanis.animehouse.presentation.activities.view_state.ErrorState
import zisis.aristofanis.animehouse.presentation.activities.view_state.LoadingState
import zisis.aristofanis.animehouse.presentation.activities.view_state.ResultState
import zisis.aristofanis.animehouse.presentation.activities.view_state.State
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.utils.InjectUtils
import zisis.aristofanis.animehouse.presentation.view_models.AnimeListViewModel

class AnimeListActivity : BaseActivity() {

    private val adapter = AnimeListAdapter { anime: Anime -> renderUserClick(anime) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animeListRecyclerView.layoutManager = LinearLayoutManager(this)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        val factory = InjectUtils.provideAnimeListViewModelFactory()
        val viewModel: AnimeListViewModel by viewModels { factory }
        viewModel.stateModel.observe(this, Observer<State> { viewState -> renderState(viewState) })
    }

    private fun renderState(state: State) {
        when (state) {
            is LoadingState -> renderLoading(state.loadingVisibility())
            is ResultState<*> -> renderQueryResult(state.result as AnimeListWithInfo)
            is ErrorState -> renderError(state.errorText)
        }
    }

    private fun renderError(errorText: String) {
        Toast.makeText(this, errorText, LENGTH_LONG).show()
    }

    private fun renderUserClick(anime: Anime) {
        Toast.makeText(this, anime.title.english, LENGTH_LONG).show()
    }

    private fun renderQueryResult(list: AnimeListWithInfo) {
        adapter.submitList(list.animeList)
        animeListRecyclerView.adapter = adapter
    }

    private fun renderLoading(visibility: Int) {
        loading.visibility = visibility
    }

}
