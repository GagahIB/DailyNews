package id.gagahib.newsapi.ui.component.source

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import id.gagahib.mylogin.ui.base.BaseActivity
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.Resource
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.data.remote.model.CountryModel
import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.newsapi.data.remote.model.SourceModel
import id.gagahib.newsapi.databinding.ActivitySourceBinding
import id.gagahib.newsapi.ui.ViewModelFactory
import id.gagahib.newsapi.ui.component.article.ArticleActivity
import id.gagahib.newsapi.utils.*
import id.gagahib.newsapi.utils.Constants.INSTANCE.BUNDLE_SOURCE
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SourceActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sourceViewModel: SourceViewModel

    private lateinit var binding: ActivitySourceBinding

    private var sourceAdapter: SourceAdapter? = null
    private var sourcesData: List<SourceModel> = emptyList()

    override fun initViewBinding() {
        binding = ActivitySourceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        sourceViewModel = viewModelFactory.create(SourceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.tvTitle.text = getString(R.string.sources)
        binding.tvTitle.setCustomFonts()
        binding.tvHeader.setCustomFonts()

        sourceViewModel.categoryData.value = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY) as CategoryModel?
        binding.toolbar.setNavigationOnClickListener { finish() }

        searchListener()
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, binding.toolbar)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun observeViewModel() {
        observe(sourceViewModel.categoryData, ::initializeView)
        observe(sourceViewModel.sourceLiveData, ::handleSourceResponse)
        observe(sourceViewModel.countryLiveData, ::handleCountryList)

        observeToast(sourceViewModel.showToast)
        observeEvent(sourceViewModel.sourceDetail, ::navigateToSourceScreen)
    }

    private fun initializeView(categoryModel: CategoryModel) {
        sourceViewModel.getSourcesByCategory(categoryModel.id)
        binding.tvHeader.text = categoryModel.name
    }

    private fun handleSourceResponse(newsResponse: Resource<NewsResponse>) {
        when (newsResponse) {
            is Resource.Loading -> binding.statusView.showLoading()
            is Resource.Success -> newsResponse.data?.let { bindListSource(newsResponse = it) }
            is Resource.DataError -> {
                binding.statusView.showError()
                newsResponse.errorCode?.let { sourceViewModel.showToastMessage(it) }
            }
        }
    }

    private fun bindListSource(newsResponse: NewsResponse) {

        binding.tvSubHeader.text = String.format("Over %d+ trustworthy news sources", newsResponse.sources.size)

        val layoutManager = LinearLayoutManager(this)
        binding.reviSources.layoutManager = layoutManager
        binding.reviSources.setHasFixedSize(true)
        sourcesData = newsResponse.sources
        sourceAdapter = SourceAdapter(sourceViewModel, sourcesData)

        sourceViewModel.generateOnceCountryCode(this)
    }

    private fun handleCountryList(countries: List<CountryModel>){
        sourceAdapter?.countries = countries
        binding.reviSources.adapter = sourceAdapter
        binding.statusView.showContent()
    }

    private fun navigateToSourceScreen(navigateEvent: Event<SourceModel>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, ArticleActivity::class.java).apply {
                putExtra(Constants.BUNDLE_SOURCE, it)
            }
            startActivity(nextScreenIntent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        binding.mainLayout.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun searchListener(){
        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(sourcesData.isNotEmpty()){
                    val filteredSource = sourcesData.filter { it.name.contains(s, true) }
                    sourceAdapter?.setItemData(filteredSource)
                }
            }
        })
    }

    private fun searchChecker(){
        RxTextView.afterTextChangeEvents(binding.etSearch)
            .skipInitialValue()
            .map {
                it.view().text.toString()
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

}