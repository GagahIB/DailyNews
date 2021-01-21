package id.gagahib.newsapi.ui.component.main

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.NewsAPIApplication
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityMainBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.source.SourceActivity
import id.gagahib.newsapi.utils.*
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_CATEGORY
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        mainViewModel = viewModelFactory.create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.daily_news)
        binding.tvTitle.setCustomFonts()
        binding.tvHeader.setCustomFonts()

        bindListCategory()

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, binding.toolbar)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun observeViewModel() {
        observeEvent(mainViewModel.newsSource, ::navigateToSourceScreen)
    }

    private fun bindListCategory() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.reviCategory.layoutManager = layoutManager
        binding.reviCategory.setHasFixedSize(true)
        val categoryAdapter = CategoryAdapter(mainViewModel, Constants.categories)
        binding.reviCategory.adapter = categoryAdapter
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