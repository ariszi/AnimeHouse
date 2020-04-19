package zisis.aristofanis.animehouse.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.apollographql.apollo.api.Error
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.presentation.presenters.HomePresenter
import zisis.aristofanis.animehouse.presentation.screen.HomeContract
import zisis.aristofanis.animehouse.presentation.utils.ActivityLifecycleObserver

class HomeActivity : BaseActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private val loading: ProgressBar by lazy { findViewById(R.id.loading) }
    private val animeListUseCase =  AnimeListUseCase(AnimeListWithInfoRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HomePresenter(this, animeListUseCase)
        lifecycle.addObserver(ActivityLifecycleObserver(listOf(animeListUseCase)))
        initListeners()
    }

    private fun initListeners() {
    }

    override fun showSuccess(animeListWithInfo: AnimeListWithInfo) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    override fun showError(errorMessage: Error) {
        Toast.makeText(this, errorMessage.message(), Toast.LENGTH_LONG).show()
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
