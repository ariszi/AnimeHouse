package zisis.aristofanis.animehouse.features.anime_details.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.core.presentation.BaseFragment
import zisis.aristofanis.animehouse.core.presentation.utils.visibilityExtension
import zisis.aristofanis.animehouse.databinding.FragmentAnimeDetailsBinding
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeDetailsFragment(val anime: Anime) : BaseFragment(R.layout.fragment_anime_details) {

    private val viewModel: AnimeDetailsViewModel by viewModels()
    private lateinit var animeTitle: TextView
    private lateinit var animeTypes: TextView
    private lateinit var animeDescription: TextView
    private lateinit var animeImage: ImageView
    private lateinit var loading: LottieAnimationView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAnimeDetailsBinding.bind(view)
        bindViews(binding)
        registerIntentActionListeners()
    }

    private fun bindViews(binding: FragmentAnimeDetailsBinding) {
        animeTitle = binding.animeTitle
        animeTypes = binding.animeTypes
        animeDescription = binding.animeDescription
        animeImage = binding.animeImage
        loading = binding.loadingAnimeDetails
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
                .into(animeImage)
        }
        animeTitle.text = animeDetails.title
        animeTypes.text = animeDetails.genre
        animeDescription.text = animeDetails.description
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        loading.visibilityExtension(visibility)
    }
}
