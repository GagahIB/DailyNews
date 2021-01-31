package id.gagahib.newsapi.ui.component

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import id.gagahib.core.utils.StatusBarUtil
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityCategoryBinding
import id.gagahib.newsapi.databinding.ActivityMainBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.category.CategoryAdapter
import id.gagahib.newsapi.ui.component.category.CategoryViewModel
import id.gagahib.newsapi.ui.component.source.SourceActivity
import id.gagahib.newsapi.utils.*
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_CATEGORY
import javax.inject.Inject
import id.gagahib.core.utils.Event


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mNavController: NavController

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupLayout(){
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        mNavController = findNavController( R.id.navHostFragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.navHostFragment)

        mNavController.navigatorProvider.addNavigator(navigator)
        mNavController.setGraph(R.navigation.nav_graph)

        appBarConfiguration = AppBarConfiguration.Builder(mNavController.graph).build()
//        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration)
    }

    override fun initializeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.tvTitle.text = getString(R.string.daily_news)
        binding.tvTitle.setCustomFonts()

        setupLayout()

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, binding.toolbar)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun observeViewModel() {
    }

    private fun navigateToSourceScreen(navigateEvent: Event<CategoryModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, SourceActivity::class.java).apply {
                putExtra(BUNDLE_CATEGORY, it)
            }
            startActivity(nextScreenIntent)
        }
    }


}