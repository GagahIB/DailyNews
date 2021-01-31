package id.gagahib.newsapi.ui.component.webview

import androidx.lifecycle.MutableLiveData
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.ErrorMapper
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.model.*
import id.gagahib.newsapi.ui.base.BaseViewModel
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
