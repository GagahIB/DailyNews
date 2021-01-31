package id.gagahib.newsapi.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.gagahib.newsapi.ui.component.article.ArticleFragment
import id.gagahib.newsapi.ui.component.category.CategoryFragment
import id.gagahib.newsapi.ui.component.source.SourceFragment
import id.gagahib.newsapi.ui.component.webview.WebViewFragment

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [CategoryFragmentModule::class])
    abstract fun bindsCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector(modules = [SourceFragmentModule::class])
    abstract fun bindsSourceFragment(): SourceFragment

    @ContributesAndroidInjector(modules = [ArticleFragmentModule::class])
    abstract fun bindsArticleFragment(): ArticleFragment

    @ContributesAndroidInjector(modules = [WebViewFragmentModule::class])
    abstract fun bindsWebViewFragment(): WebViewFragment


}
