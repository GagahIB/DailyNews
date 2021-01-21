package id.gagahib.mylogin.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import id.gagahib.newsapi.ui.base.BaseView
import id.gagahib.newsapi.ui.base.BaseViewModel

abstract class BaseActivity : AppCompatActivity(),
    BaseView {

    protected lateinit var baseViewModel: BaseViewModel

    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewBinding()
        initializeViewModel()
        observeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
