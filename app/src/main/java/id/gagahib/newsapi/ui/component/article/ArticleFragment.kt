package id.gagahib.newsapi.ui.component.article

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import id.gagahib.mylogin.ui.base.BaseFragment
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.databinding.*
import id.gagahib.newsapi.utils.*
import id.gagahib.core.utils.Event
import id.gagahib.core.utils.observe
import id.gagahib.core.utils.observeEvent
import id.gagahib.core.remote.Resource


class ArticleFragment : BaseFragment<ArticleViewModel>() {


    override val layoutId: Int
        get() = R.layout.fragment_article

    private lateinit var binding: FragmentArticleBinding

    override val viewModelType: Class<ArticleViewModel>
        get() = ArticleViewModel::class.java


    private var articleAdapter: ArticleAdapter? = null
    private var articleData: List<ArticleModel> = emptyList()

    private var pageSize: Int = 10
    private var page: Int = 1
    private var source: String = ""
    private var keyword: String = ""
    private var loadMore: Boolean = false

    override fun initViewBinding(view: View) {
        binding = FragmentArticleBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            viewModel.sourceData.value = arguments?.getSerializable(Constants.BUNDLE_SOURCE) as SourceModel?
            setLayout()
            initSearchListener()
        }

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
    }
    override fun observeViewModel() {
        observe(viewModel.sourceData, ::initializeView)
        observe(viewModel.articleLiveData, ::handleSourceResponse)

        observeToast(viewModel.showToast)
        observeEvent(viewModel.articleDetail, ::navigateToDetailScreen)
    }

    private fun initializeView(sourceModel: SourceModel) {
        source = sourceModel.id
        viewModel.getNewsBySources(source, pageSize, page, keyword)
        articleAdapter?.sourceName = sourceModel.name
    }

    private fun handleSourceResponse(newsResponse: Resource<NewsResponse>) {
        when (newsResponse) {
            is Resource.Loading -> if (!loadMore){binding.statusView.showLoading()}
            is Resource.Success -> newsResponse.data?.let { bindListArticle(newsResponse = it) }
            is Resource.DataError -> {
                binding.statusView.showError()
                newsResponse.errorCode?.let {
                    viewModel.showToastMessage(it)
                }
            }
        }
    }

    private fun setLayout(){
        val layoutManager = LinearLayoutManager(context)
        binding.reviArticles.layoutManager = layoutManager
        binding.reviArticles.setHasFixedSize(true)
        articleAdapter = ArticleAdapter(viewModel, articleData)
        binding.reviArticles.adapter = articleAdapter

        binding.reviArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = binding.reviArticles.childCount
                    val itemCount = (binding.reviArticles.layoutManager as LinearLayoutManager).itemCount
                    val firstVisibleItem = (binding.reviArticles.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadMore) {
                            requestSearch(keyword, ++page, true)
                        }
                    }
                }
            }
        })
    }

    private fun bindListArticle(newsResponse: NewsResponse) {
        if (newsResponse.articles.isNotEmpty()){
            if (loadMore){
                loadMore = false
                articleAdapter?.addItemData(newsResponse.articles)
                Log.d("Article", "Add : "+newsResponse.articles.size)
            }else{
                articleAdapter?.setItemData(newsResponse.articles)
                Log.d("Article", "New : "+newsResponse.articles.size)
            }
            binding.statusView.showContent()
        }else{
            if(!loadMore)
                binding.statusView.showEmpty()
        }
    }

    private fun navigateToDetailScreen(navigateEvent: Event<ArticleModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val bundle = bundleOf(
                Pair(Constants.BUNDLE_ARTICLE, it),
                Pair("title", it.source.name)
            )
            findNavController().navigate(
                R.id.action_articleFragment_to_webViewFragment,
                bundle,
                null,
                null
            )
        }
    }

    private fun initSearchListener(){
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                requestSearch(v.text.toString(), 1, false)
                true
            } else {
                false
            }
        }
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        binding.mainLayout.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun requestSearch(keyword: String, paging: Int, isLoadMore: Boolean){
        page = paging
        loadMore = isLoadMore
        viewModel.getNewsBySources(source, pageSize, page, keyword)
        binding.mainLayout.hideKeyboard()
    }
}