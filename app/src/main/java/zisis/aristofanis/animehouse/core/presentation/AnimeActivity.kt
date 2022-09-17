package zisis.aristofanis.animehouse.core.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.features.anime_list.presentation.AnimeListFragment

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
            add<AnimeListFragment>(R.id.fragment_container)
        }
    }

}
