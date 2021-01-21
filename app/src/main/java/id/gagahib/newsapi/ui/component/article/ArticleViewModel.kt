package id.gagahib.newsapi.ui.component.article

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.mapper.ErrorMapper
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.Resource
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.ui.base.BaseViewModel
import id.gagahib.newsapi.utils.Event
import id.gagahib.newsapi.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ArticleViewModel @Inject
constructor(private val dateRepositorySource: DataRepositorySource) : BaseViewModel() {

    override val errorManager
    get() = ErrorManager(ErrorMapper())

    var sourceData: MutableLiveData<SourceModel> = MutableLiveData()

    /**
     *
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val _articleDetail = MutableLiveData<Event<ArticleModel>>()
    val articleDetail: LiveData<Event<ArticleModel>> get() = _articleDetail

    val _articleLiveData = MutableLiveData<Resource<NewsResponse>>()
    val articleLiveData: LiveData<Resource<NewsResponse>> get() = _articleLiveData

    private val showToastPrivate = MutableLiveData<Event<Any>>()
    val showToast: LiveData<Event<Any>> get() = showToastPrivate

    fun openArticleDetail(articleModel: ArticleModel) {
        _articleDetail.value = Event(articleModel)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = Event(error.description)
    }

    fun getNewsBySources(
        sources: String,
        pageSize: Int,
        page: Int,
        q: String) {
        viewModelScope.launch {
            _articleLiveData.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dateRepositorySource.getNewsBySources(
                    sources,
                    pageSize,
                    page,
                    q
                ).collect {
                    _articleLiveData.value = it
                }
            }
        }
    }

}
