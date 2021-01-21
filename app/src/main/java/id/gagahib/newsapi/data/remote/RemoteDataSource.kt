package id.gagahib.newsapi.data.remote

import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.mylogin.data.remote.payload.NewsPayload

interface RemoteDataSource {

    suspend fun getSourcesByCategory(category: String): Resource<NewsResponse>

    suspend fun getNewsBySources(
        sources: String,
        pageSize: Int,
        page: Int,
        q: String
    ): Resource<NewsResponse>

}
