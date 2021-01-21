/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.gagahib.newsapi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.article.ArticleViewModel
import id.gagahib.newsapi.ui.component.main.MainViewModel
import id.gagahib.newsapi.ui.component.source.SourceViewModel
import id.gagahib.newsapi.ui.component.webview.WebViewModel

/**
 * Since dagger
 * support multibinding you are free to bind as may ViewModels as you want.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel::class)
    abstract fun bindSourceViewModel(viewModel: SourceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    abstract fun bindArticleViewModel(viewModel: ArticleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebViewModel::class)
    abstract fun bindWebViewModel(viewModel: WebViewModel): ViewModel

}
