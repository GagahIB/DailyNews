package id.gagahib.newsapi.ui.base.listeners

import id.gagahib.newsapi.data.remote.model.ArticleModel

interface RecyclerItemListener {
    fun onItemSelected(articleModel: ArticleModel)
}
