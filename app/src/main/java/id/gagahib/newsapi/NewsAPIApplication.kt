package id.gagahib.newsapi

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import id.gagahib.newsapi.di.DaggerAppComponent
import javax.inject.Inject


class NewsAPIApplication : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initDagger()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    open fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    companion object {
        lateinit var context: Context
    }

}
