package zisis.aristofanis.animehouse.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Error
import kotlinx.android.synthetic.main.activity_main.*
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.adapters.AnimeListAdapter
import zisis.aristofanis.animehouse.presentation.presenters.HomePresenter
import zisis.aristofanis.animehouse.presentation.screen.HomeContract
import zisis.aristofanis.animehouse.presentation.utils.ActivityLifecycleObserver

class HomeActivity : BaseActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val animeListUseCase = AnimeListUseCase(AnimeListWithInfoRepository())
    private lateinit var adapter: AnimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HomePresenter(this, animeListUseCase)
        lifecycle.addObserver(ActivityLifecycleObserver(listOf(animeListUseCase)))
        animeListRecyclerView.layoutManager = linearLayoutManager

        initListeners()
    }

    private fun initListeners() {
    }

    override fun showSuccess(animeListWithInfo: AnimeListWithInfo) {
        adapter = AnimeListAdapter(animeListWithInfo.animeList)
        animeListRecyclerView.adapter = adapter
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    override fun showError(errorMessage: Error) {
        Toast.makeText(this, errorMessage.message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.terminate()
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }
}
