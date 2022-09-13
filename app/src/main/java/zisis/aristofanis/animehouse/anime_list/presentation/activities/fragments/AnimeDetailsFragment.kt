package zisis.aristofanis.animehouse.anime_list.presentation.activities.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_anime_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.di.ViewModelFactory
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
    private val viewModel by assistedViewModel {
        viewModelFactory.create(params = anime)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerIntentActionListeners()
    }

    private fun registerIntentActionListeners() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: AnimeDetailsContract.AnimeDetailsState) {
        navigate(viewState.navigateBack)
        showAnimeDetails(viewState.animeInfoSubState)
        renderLoading(viewState.loading)
    }

    private fun navigate(navigateBack: Boolean) {

    }

    private fun showAnimeDetails(animeDetails: AnimeDetailsContract.AnimeInfoSubState) {
        activity?.let {
            Glide.with(it)
                .load(animeDetails.image)
                .into(anime_image)
        }
        anime_title.text = animeDetails.title
        anime_types.text = animeDetails.genre
        anime_description.text = animeDetails.description
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        loading_anime_details.visibilityExtension(visibility)
    }
}
