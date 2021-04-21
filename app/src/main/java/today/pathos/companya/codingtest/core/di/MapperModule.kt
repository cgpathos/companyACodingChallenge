package today.pathos.companya.codingtest.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import today.pathos.companya.codingtest.data.network.NetworkImageMapper
import today.pathos.companya.codingtest.data.network.NetworkVideoMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Singleton
    @Provides
    fun provideNetworkImageMapper() : NetworkImageMapper {
        return NetworkImageMapper()
    }
    @Singleton
    @Provides
    fun provideNetworkVideoMapper() : NetworkVideoMapper {
        return NetworkVideoMapper()
    }

}