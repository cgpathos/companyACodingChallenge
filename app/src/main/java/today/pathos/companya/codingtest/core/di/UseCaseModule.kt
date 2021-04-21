package today.pathos.companya.codingtest.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import today.pathos.companya.codingtest.data.local.MemoryFavoriteRepository
import today.pathos.companya.codingtest.data.network.NetworkSearchRepository
import today.pathos.companya.codingtest.domain.interactor.*

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideSearchUseCase(repository: NetworkSearchRepository) = SearchUseCase(repository)

    @Provides
    fun provideAddFavoriteUseCase(repository: MemoryFavoriteRepository) = AddFavoriteUseCase(repository)

    @Provides
    fun provideDelFavoriteUseCase(repository: MemoryFavoriteRepository) = DelFavoriteUseCase(repository)

    @Provides
    fun provideFavoriteUseCase(repository: MemoryFavoriteRepository) = FavoriteListUseCase(repository)

    @Provides
    fun provideClearFavoriteUseCase(repository: MemoryFavoriteRepository) = ClearFavoriteUseCase(repository)
}