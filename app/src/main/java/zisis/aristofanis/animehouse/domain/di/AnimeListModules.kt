package zisis.aristofanis.animehouse.domain.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.datasources.AnimeListRepositoryContract

@InstallIn(ViewModelComponent::class)
@Module
class AnimeListModules {

    @ViewModelScoped
    @Provides
    fun provideAnimeListRepository(api: ApolloClient): AnimeListRepositoryContract {
        return AnimeListWithInfoRepository(api)
    }

}
