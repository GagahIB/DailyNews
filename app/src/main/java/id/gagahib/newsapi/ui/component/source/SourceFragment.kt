package id.gagahib.newsapi.ui.component.source

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.gagahib.core.utils.Event
import id.gagahib.core.utils.observe
import id.gagahib.mylogin.ui.base.BaseFragment
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.data.remote.model.CountryModel
import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.newsapi.data.remote.model.SourceModel
import id.gagahib.newsapi.databinding.FragmentSourceBinding
import id.gagahib.newsapi.utils.Constants
import id.gagahib.newsapi.utils.setCustomFonts
import id.gagahib.newsapi.utils.showToast
import id.gagahib.core.remote.Resource


class SourceFragment : BaseFragment<SourceViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_source

    private lateinit var binding: FragmentSourceBinding

    override val viewModelType: Class<SourceViewModel>
        get() = SourceViewModel::class.java

    private var sourceAdapter: SourceAdapter? = null
    private var sourcesData: List<SourceModel> = emptyList()

    private val args: SourceFragmentArgs by navArgs()

    override fun initViewBinding(view: View) {
        binding = FragmentSourceBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            Log.d("InitView", "Called")
            viewModel.categoryData.value =
                arguments?.getSerializable(Constants.BUNDLE_CATEGORY) as CategoryModel?
            binding.tvHeader.setCustomFonts()
            setupLayout()
            searchListener()
        }
    }

    private fun setupLayout() {
        sourceAdapter = SourceAdapter(viewModel, sourcesData)
        val layoutManager = LinearLayoutManager(context)
        binding.reviSources.layoutManager = layoutManager
        binding.reviSources.setHasFixedSize(true)
        binding.reviSources.adapter = sourceAdapter
    }

    override fun observeViewModel() {
        observe(viewModel.categoryData, ::initializeView)
        observe(viewModel.sourceLiveData, ::handleSourceResponse)
        observe(viewModel.countryLiveData, ::handleCountryList)
        observe(viewModel.sourceDetail, ::navigateToArticleScreen)

        observeToast(viewModel.showToast)
    }

    private fun initializeView(categoryModel: CategoryModel) {
        Log.d("Source", "initializeView Called")
        viewModel.getSourcesByCategory(categoryModel.id)
        binding.tvHeader.text = categoryModel.name

    }

    private fun handleSourceResponse(newsResponse: Resource<NewsResponse>) {
        when (newsResponse) {
            is Resource.Loading -> binding.statusView.showLoading()
            is Resource.Success -> newsResponse.data?.let { handleListSource(newsResponse = it) }
            is Resource.DataError -> {
                binding.statusView.showError()
                newsResponse.errorCode?.let {
                    viewModel.showToastMessage(it)
                }
            }
        }
    }

    private fun handleListSource(newsResponse: NewsResponse) {
        binding.tvSubHeader.text =
            String.format("Over %d+ trustworthy news sources", newsResponse.sources.size)
        sourcesData = newsResponse.sources
        sourceAdapter?.setItemData(newsResponse.sources)
        context?.let { viewModel.generateOnceCountryCode(it) }

        Log.d(
            "Source",
            "Notify :" + sourceAdapter?.itemCount + "|" + sourceAdapter?.countries?.size
        )
    }

    private fun handleCountryList(countries: List<CountryModel>) {
        sourceAdapter?.countries = countries
        sourceAdapter?.notifyDataSetChanged()

        Log.d(
            "Source",
            "Notify :" + sourceAdapter?.itemCount + "|" + sourceAdapter?.countries?.size
        )
        binding.statusView.showContent()
    }

    private fun navigateToArticleScreen(navigateEvent: Event<SourceModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val bundle = bundleOf(
                Pair(Constants.BUNDLE_SOURCE, it),
                Pair("title", it.name)
            )
            findNavController().navigate(
                R.id.action_sourceFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        binding.mainLayout.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun searchListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                generateFilter(s.toString())
            }
        })
    }

    private fun generateFilter(keyword: String) {
        if (sourcesData.isNotEmpty()) {
            val filteredSource = sourcesData.filter { it.name.contains(keyword, true) }
            sourceAdapter?.setItemDataNotify(filteredSource)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("OnDestroy", "Source")
    }

}