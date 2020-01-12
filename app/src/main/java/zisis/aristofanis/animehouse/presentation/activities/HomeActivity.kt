package zisis.aristofanis.animehouse.presentation.activities

import AnimeListQuery
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.apollographql.apollo.api.Error
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.data.AnimeListClient
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.AnimeListUseCase
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.presentation.presenters.HomePresenter
import zisis.aristofanis.animehouse.presentation.screen.HomeContract

class HomeActivity : BaseActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private val button: Button by lazy { findViewById<Button>(R.id.theButton) }
    private val loading: ProgressBar by lazy { findViewById<ProgressBar>(R.id.loading) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HomePresenter(
            this, AnimeListUseCase(
                AnimeListWithInfoRepository(
                    AnimeListClient(query = AnimeListQuery.builder().build())
                )
            )
        )
        initListeners()
    }

    private fun initListeners() {
        button.setOnClickListener { presenter.getAnimeSon() }
    }

    override fun showSuccess(data: AnimeListWithInfo) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }

    override fun showError(error: Error) {
        Toast.makeText(this, error.message(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.terminate()
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading() {
        button.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
        button.visibility = View.VISIBLE
    }

}
