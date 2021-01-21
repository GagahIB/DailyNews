package id.gagahib.newsapi.ui.component.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.ArticleModel
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityWebviewBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.utils.*
import javax.inject.Inject


class WebViewActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var webViewModel: WebViewModel

    private lateinit var binding: ActivityWebviewBinding

    override fun initViewBinding() {
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        webViewModel = viewModelFactory.create(WebViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webViewModel.articleData.value = intent.getSerializableExtra(Constants.BUNDLE_ARTICLE) as ArticleModel?

        binding.toolbar.setNavigationOnClickListener { finish() }

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, binding.toolbar)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun observeViewModel() {
        observe(webViewModel.articleData, ::initializeView)
    }

    private fun initializeView(articleModel: ArticleModel) {
        binding.tvTitle.text = articleModel.title
        binding.tvTitle.setCustomFonts()

        val webViewClient: WebViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.swipeRefresh.isRefreshing = true
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.swipeRefresh.isRefreshing = false
                super.onPageFinished(view, url)
            }
        }

        binding.webView.webViewClient = webViewClient
        binding.webView.loadUrl(articleModel.url)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.clearCache(true)

        binding.swipeRefresh.setOnRefreshListener {
            binding.webView.loadUrl(articleModel.url)
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else
            super.onBackPressed()
    }

}