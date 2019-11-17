package zisis.aristofanis.animehouse.presentation

import AnimeListQuery
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.apollographql.apollo.api.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.R
import zisis.aristofanis.animehouse.data.AnimeListClient
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.AnimeListUseCase
import zisis.aristofanis.animehouse.domain.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.QueryData
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAnimeSon()
    }

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private fun getAnimeSon() {
         launch {
            val response =
                AnimeListUseCase(AnimeListWithInfoRepository(AnimeListClient(query = AnimeListQuery.builder().build()))).getAnimeListWithInfo()
            when (response){
                is QueryData.Success<*> -> showSuccess(response.data as AnimeListWithInfo)
                is QueryData.Error -> showError(response.errorMessage)
            }
        }
    }

    private fun showSuccess(data: AnimeListWithInfo) {
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        Log.d("TEST", data.animeList[0].description)
    }

    private fun showError(error: Error) {
       Toast.makeText(this,error.message(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
