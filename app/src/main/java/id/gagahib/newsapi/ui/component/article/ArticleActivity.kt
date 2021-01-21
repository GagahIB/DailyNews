package id.gagahib.newsapi.ui.component.article

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.NewsAPIApplication
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.error.Error
import id.gagahib.newsapi.data.remote.Resource
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.databinding.ActivityArticleBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.webview.WebViewActivity
import id.gagahib.newsapi.utils.*
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_ARTICLE
import javax.inject.Inject


class ArticleActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var articleViewModel: ArticleViewModel

    private lateinit var binding: ActivityArticleBinding

    private var articleAdapter: ArticleAdapter? = null
    private var articleData: List<ArticleModel> = emptyList()

    private var pageSize: Int = 10
    private var page: Int = 1
    private var source: String = ""
    private var keyword: String = ""
    private var loadMore: Boolean = false

    override fun initViewBinding() {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        articleViewModel = viewModelFactory.create(ArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.setCustomFonts()
        binding.toolbar.setNavigationOnClickListener { finish() }

        articleViewModel.sourceData.value = intent.getSerializableExtra(Constants.BUNDLE_SOURCE) as SourceModel?

        initArticleAdapter()
        initSearchListener()
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, binding.toolbar)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun observeViewModel() {
        observe(articleViewModel.sourceData, ::initializeView)
        observe(articleViewModel.articleLiveData, ::handleSourceResponse)

        observeToast(articleViewModel.showToast)
        observeEvent(articleViewModel.articleDetail, ::navigateToDetailScreen)
    }

    private fun initializeView(sourceModel: SourceModel) {
        binding.tvTitle.text = sourceModel.name
        source = sourceModel.id
        articleViewModel.getNewsBySources(source, pageSize, page, keyword)
        articleAdapter?.sourceName = sourceModel.name
    }

    private fun handleSourceResponse(newsResponse: Resource<NewsResponse>) {
        when (newsResponse) {
            is Resource.Loading -> if (!loadMore){binding.statusView.showLoading()}
            is Resource.Success -> newsResponse.data?.let { bindListArticle(newsResponse = it) }
            is Resource.DataError -> {
                binding.statusView.showError()
                newsResponse.errorCode?.let {
                    articleViewModel.showToastMessage(it)
                }
            }
        }
    }

    private fun initArticleAdapter(){
        val layoutManager = LinearLayoutManager(this)
        binding.reviArticles.layoutManager = layoutManager
        binding.reviArticles.setHasFixedSize(true)
        articleAdapter = ArticleAdapter(articleViewModel, articleData)
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

        Log.d("Article", "Size : "+articleAdapter?.itemCount)
    }

    private fun navigateToDetailScreen(navigateEvent: Event<ArticleModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, WebViewActivity::class.java).apply {
                putExtra(BUNDLE_ARTICLE, it)
            }
            startActivity(nextScreenIntent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
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
        articleViewModel.getNewsBySources(source, pageSize, page, keyword)
        binding.mainLayout.hideKeyboard()
    }
}