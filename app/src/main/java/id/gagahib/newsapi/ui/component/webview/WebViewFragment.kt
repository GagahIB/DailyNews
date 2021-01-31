package id.gagahib.newsapi.ui.component.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import id.gagahib.core.utils.observe
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.mylogin.ui.base.BaseFragment
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.ArticleModel
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.ActivityWebviewBinding
import id.gagahib.newsapi.databinding.FragmentSourceBinding
import id.gagahib.newsapi.databinding.FragmentWebviewBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.source.SourceViewModel
import id.gagahib.newsapi.utils.*
import javax.inject.Inject


class WebViewFragment : BaseFragment<WebViewModel>()   {


    override val layoutId: Int
        get() = R.layout.fragment_webview

    private lateinit var binding: FragmentWebviewBinding

    override val viewModelType: Class<WebViewModel>
        get() = WebViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.articleData.value = arguments?.getSerializable(Constants.BUNDLE_ARTICLE) as ArticleModel?
    }

    override fun initViewBinding(view: View) {
        binding = FragmentWebviewBinding.bind(view)
    }

    override fun observeViewModel() {
        observe(viewModel.articleData, ::initializeView)
    }

    private fun initializeView(articleModel: ArticleModel) {
        val webViewClient: WebViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                binding.swipeRefresh.isRefreshing = true
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

//    override fun onBackPressed() {
//        if (binding.webView.canGoBack())
//            binding.webView.goBack()
//        else
//            super.onBackPressed()
//    }

}