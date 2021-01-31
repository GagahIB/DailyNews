/*
 * Copyright (C) 2017 The Android Open Source Project
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

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.gagahib.newsapi.ui.component.MainActivity
import id.gagahib.newsapi.ui.component.article.ArticleActivity
import id.gagahib.newsapi.ui.component.category.CategoryActivity
import id.gagahib.newsapi.ui.component.category.CategoryFragment
import id.gagahib.newsapi.ui.component.source.SourceActivity
import id.gagahib.newsapi.ui.component.webview.WebViewActivity

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeCategoryActivity(): CategoryActivity

    @ContributesAndroidInjector()
    abstract fun contributeSourceActivity(): SourceActivity

    @ContributesAndroidInjector()
    abstract fun contributeArticleActivity(): ArticleActivity

    @ContributesAndroidInjector()
    abstract fun contributeWebActivity(): WebViewActivity

}