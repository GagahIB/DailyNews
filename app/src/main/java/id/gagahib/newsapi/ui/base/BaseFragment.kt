package id.gagahib.mylogin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import id.gagahib.newsapi.ui.base.BaseView
import id.gagahib.newsapi.ui.base.BaseViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.utils.Constants
import id.gagahib.newsapi.utils.setCustomFonts
import javax.inject.Inject

abstract class BaseFragment<V : BaseViewModel>  : DaggerFragment(), BaseView {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var viewModel: V
        private set

    abstract val layoutId: Int

//    protected abstract val bindingVariableId: Int
    protected abstract val viewModelType: Class<V>


//    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    protected abstract fun initViewBinding(view: View)


    var hasInitializedRootView = false
    private var rootView: View? = null

    fun getPersistentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, layout: Int): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout,container,false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.getParent() as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        var view = inflater.inflate(layoutId, container, false)
//        return view
        return getPersistentView(inflater, container, savedInstanceState, layoutId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = generateViewModel()
        initViewBinding(view)
        observeViewModel()

    }

    private fun generateViewModel(): V {
        return ViewModelProvider(this, factory).get(viewModelType)
    }


}
