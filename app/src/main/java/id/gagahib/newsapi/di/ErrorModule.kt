package id.gagahib.newsapi.di

import dagger.Binds
import dagger.Module
import id.gagahib.newsapi.data.error.ErrorMapper
import id.gagahib.core.data.error.ErrorFactory
import id.gagahib.core.data.error.ErrorMapperInterface
import id.gagahib.newsapi.data.error.ErrorManager
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
