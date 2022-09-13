package zisis.aristofanis.animehouse.anime_list.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.anime_list.presentation.activities.fragments.AnimeListFragmentV2
import zisis.aristofanis.animehouse.anime_list.presentation.core.BaseActivity
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.AnimeViewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class AnimeActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachAnimeListFragment()
    }

    private fun attachAnimeListFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<AnimeListFragmentV2>(R.id.fragment_container)
        }
    }

}
