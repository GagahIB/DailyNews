package id.gagahib.newsapi.di

import dagger.Binds
import dagger.Module
import id.gagahib.newsapi.data.error.mapper.ErrorMapper
import id.gagahib.newsapi.data.error.ErrorFactory
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.mapper.ErrorMapperInterface
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorFactory

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperInterface
}
