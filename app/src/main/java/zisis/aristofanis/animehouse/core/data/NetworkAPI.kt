package zisis.aristofanis.animehouse.core.data

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

interface NetworkAPI {
    fun api(okHttpClient: OkHttpClient): ApolloClient
}
