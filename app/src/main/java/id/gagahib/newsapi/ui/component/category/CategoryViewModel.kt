package id.gagahib.newsapi.ui.component.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.gagahib.newsapi.data.error.ErrorManager
import id.gagahib.newsapi.data.error.ErrorMapper
import id.gagahib.newsapi.data.remote.DataRepositorySource
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.ui.base.BaseViewModel
import id.gagahib.core.utils.Event
import javax.inject.Inject

class CategoryViewModel @Inject
constructor(private val dateRepositorySource: DataRepositorySource) : BaseViewModel() {

    override val errorManager
    get() = ErrorManager(ErrorMapper())

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val newsSourcePrivate = MutableLiveData<Event<CategoryModel>>()
    val newsSource: LiveData<Event<CategoryModel>> get() = newsSourcePrivate


    fun openNewsSource(categoryModel: CategoryModel) {
        newsSourcePrivate.value = Event(categoryModel)
    }



}
