package zisis.aristofanis.animehouse.presentation

import AnimeListQuery
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import org.jetbrains.annotations.NotNull
import zisis.aristofanis.animehouse.data.RestProvider
import zisis.aristofanis.animehouse.R

class MainActivity : AppCompatActivity() {

    private lateinit var getAnimeList: AnimeListQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAnimeSon()
    }

    private fun getAnimeSon() {
        // Init Query
        getAnimeList = AnimeListQuery.builder().build()
        RestProvider.client.query(getAnimeList).enqueue(object : ApolloCall.Callback<AnimeListQuery.Data>() {
            override fun onFailure(error: ApolloException) {
                Log.d("Todo", error.toString())
            }

            override fun onResponse(@NotNull response: Response<AnimeListQuery.Data>) {
                // Changing UI must be on UI thread
                Log.d("Todo", response.data().toString())
            }
        })
    }
}
