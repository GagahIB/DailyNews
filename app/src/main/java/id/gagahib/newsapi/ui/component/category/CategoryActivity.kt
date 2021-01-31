package id.gagahib.newsapi.ui.component.category

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import id.gagahib.core.utils.StatusBarUtil
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityCategoryBinding
import id.gagahib.newsapi.databinding.ActivityMainBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.source.SourceActivity
import id.gagahib.newsapi.utils.*
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_CATEGORY
import javax.inject.Inject
import id.gagahib.core.utils.Event
import id.gagahib.core.utils.observeEvent


class CategoryActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var categoryViewModel: CategoryViewModel

    private lateinit var binding: ActivityCategoryBinding

    override fun initViewBinding() {
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        categoryViewModel = viewModelFactory.create(CategoryViewModel::class.java)
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
        observeEvent(categoryViewModel.newsSource, ::navigateToSourceScreen)
    }

    private fun bindListCategory() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.reviCategory.layoutManager = layoutManager
        binding.reviCategory.setHasFixedSize(true)
        val categoryAdapter = CategoryAdapter(categoryViewModel, Constants.categories)
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