package com.example.core

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RestProvider  {

    private val BASE_URL = "https://graphql.anilist.co/graphql"

    @Singleton
    @Provides
    fun log() = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                chain.proceed(builder.build())
            }
            .build()


    @Singleton
    @Provides
    fun api(okHttpClient: OkHttpClient): ApolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .okHttpClient(okHttpClient)
        .build()

}
