package zisis.aristofanis.animehouse.anime_list.presentation.activities.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_anime_details.*
import kotlinx.android.synthetic.main.fragment_anime_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.di.ViewModelFactory
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeData
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeDetailsContract
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.assistedViewModel
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeDetailsFragment(val anime: Anime) : BaseFragment(R.layout.fragment_anime_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    //private val viewModel: AnimeDetailsViewModel by viewModels()

    private val viewModel by assistedViewModel {
        viewModelFactory.create(params = anime)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerIntentActionListeners()
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.eventSideEffects.collect { renderSideEffects(it) } }
        lifecycleScope.launch { viewModel.state.collect { renderState(it) } }
    }

    private fun renderState(viewState: AnimeDetailsContract.ViewState) {
        when (viewState.data) {
            is AnimeData.AnimeDetails -> renderAnime(viewState.data)
        }
    }

    private fun renderAnime(data: AnimeData.AnimeDetails) {
        activity?.let {
            Glide.with(it)
                .load(data.anime.image)
                .centerCrop()
                .into(anime_image)
        }
        anime_title.text = data.anime.title.english
        anime_types.text = data.anime.genres?.joinToString()
        anime_description.text = data.anime.description
    }

    private fun renderSideEffects(sideEffect: AnimeDetailsContract.ViewEffects) {

    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
