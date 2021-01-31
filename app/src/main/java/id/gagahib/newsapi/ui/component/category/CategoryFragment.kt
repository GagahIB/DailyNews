package id.gagahib.newsapi.ui.component.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.gagahib.mylogin.ui.base.BaseFragment
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityArticleBinding
import id.gagahib.newsapi.databinding.ActivityCategoryBinding
import id.gagahib.newsapi.databinding.FragmentCategoryBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.utils.Constants
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_CATEGORY
import id.gagahib.core.utils.Event
import id.gagahib.core.utils.observeEvent
import id.gagahib.newsapi.utils.setCustomFonts
import javax.inject.Inject


class CategoryFragment : BaseFragment<CategoryViewModel>() {


    override val layoutId: Int
        get() = R.layout.fragment_category

    private lateinit var binding: FragmentCategoryBinding

    override val viewModelType: Class<CategoryViewModel>
        get() = CategoryViewModel::class.java

    companion object {
        fun getInstance(): CategoryFragment = CategoryFragment()
    }

    override fun initViewBinding(view: View) {
        binding = FragmentCategoryBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeader.setCustomFonts()
        setupLayout()
    }

    override fun observeViewModel() {
        observeEvent(viewModel.newsSource, ::navigateToSourceScreen)
    }

    private fun setupLayout(){
        val layoutManager = GridLayoutManager(context, 2)
        binding.reviCategory.layoutManager = layoutManager
        binding.reviCategory.setHasFixedSize(true)
        val categoryAdapter = CategoryAdapter(viewModel, Constants.categories)
        binding.reviCategory.adapter = categoryAdapter
    }

    private fun navigateToSourceScreen(navigateEvent: Event<CategoryModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val bundle = bundleOf(
                Pair(BUNDLE_CATEGORY, it),
                Pair("title", it.name)
            )
            findNavController().navigate(
                R.id.action_categoryFragment_to_sourceFragment,
                bundle,
                null,
                null
            )
        }
    }

}