package id.gagahib.newsapi.ui.component.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.mapper.ErrorMapper
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.Resource
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.data.remote.model.CountryModel
import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.newsapi.data.remote.model.SourceModel
import id.gagahib.newsapi.ui.base.BaseViewModel
import id.gagahib.newsapi.utils.Event
import id.gagahib.newsapi.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class SourceViewModel @Inject
constructor(private val dateRepositorySource: DataRepositorySource) : BaseViewModel() {

    override val errorManager
    get() = ErrorManager(ErrorMapper())

    var categoryData: MutableLiveData<CategoryModel> = MutableLiveData()

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val _sourceDetail = MutableLiveData<Event<SourceModel>>()
    val sourceDetail: LiveData<Event<SourceModel>> get() = _sourceDetail

    val _sourceLiveData = MutableLiveData<Resource<NewsResponse>>()
    val sourceLiveData: LiveData<Resource<NewsResponse>> get() = _sourceLiveData

    var _countryLiveData = MutableLiveData<List<CountryModel>>()
    val countryLiveData: LiveData<List<CountryModel>> get() = _countryLiveData

    private val showToastPrivate = MutableLiveData<Event<Any>>()
    val showToast: LiveData<Event<Any>> get() = showToastPrivate


    fun openSourceDetail(sourceModel: SourceModel) {
        _sourceDetail.value = Event(sourceModel)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = Event(error.description)
    }

    fun getSourcesByCategory(category: String) {
        viewModelScope.launch {
            _sourceLiveData.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dateRepositorySource.getSourcesByCategory(category).collect {
                    _sourceLiveData.value = it
                }
            }
        }
    }

    fun generateOnceCountryCode(context: Context){
        val jsonFileString = getJsonDataFromAsset(context)
        val listCountryType = object : TypeToken<List<CountryModel>>() {}.type
        if (jsonFileString != null){
            _countryLiveData.value = Gson().fromJson(jsonFileString, listCountryType)
        }

    }


    private fun getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("json/country_code.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


}
