package zisis.aristofanis.animehouse.data.api.network

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

interface NetworkAPI {
    fun api(okHttpClient: OkHttpClient): ApolloClient
}
