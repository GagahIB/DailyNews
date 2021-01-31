package id.gagahib.newsapi.di

import dagger.Binds
import dagger.Module
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.RemoteDataRepository
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideDataRepository(remoteDataRepository: RemoteDataRepository): DataRepositorySource
}
