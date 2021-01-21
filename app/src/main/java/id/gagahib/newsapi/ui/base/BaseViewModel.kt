package id.gagahib.newsapi.ui.base

import androidx.lifecycle.ViewModel
import id.gagahib.newsapi.data.error.ErrorManager

abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    abstract val errorManager: ErrorManager

}
