package id.gagahib.newsapi.ui.component.webview

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
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.ui.base.BaseViewModel
import id.gagahib.newsapi.utils.Event
import id.gagahib.newsapi.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class WebViewModel @Inject
constructor(private val dateRepositorySource: DataRepositorySource) : BaseViewModel() {

    override val errorManager
    get() = ErrorManager(ErrorMapper())

    var articleData: MutableLiveData<ArticleModel> = MutableLiveData()

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */


}
