package id.gagahib.newsapi.ui.component.article

import androidx.lifecycle.*
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.ErrorMapper
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.ui.base.BaseViewModel
import id.gagahib.core.utils.Event
import id.gagahib.newsapi.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import id.gagahib.core.remote.Resource
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


    val currentNews: LiveData<Resource<NewsResponse>> = liveData {
//        emit(Resource.Loading())
        emitSource(dateRepositorySource.getNewsBySources("",1,1,"")
            .onStart { emit(Resource.Loading()) }
            .map { doSomeThing(it) }
            .debounce(1000)
            .asLiveData())
    }

    fun doSomeThing(response: Resource<NewsResponse>): Resource<NewsResponse>{

        return response
    }

}
