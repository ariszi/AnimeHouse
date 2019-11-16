package zisis.aristofanis.animehouse.data

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RestProvider {
    private const val BASE_URL = "https://graphql.anilist.co/graphql"

    private  val log: HttpLoggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(log)
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method, original.body)
            chain.proceed(builder.build())
        }
        .build()

    val client: ApolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .okHttpClient(okHttpClient)
        .build()
}
