package id.gagahib.newsapi.data.remote

import id.gagahib.newsapi.data.remote.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import id.gagahib.core.remote.Resource

interface DataRepositorySource {

    suspend fun getSourcesByCategory(category: String): Flow<Resource<NewsResponse>>
    suspend fun getNewsBySources(
        sources: String,
        pageSize: Int,
        page: Int,
        q: String
    ): Flow<Resource<NewsResponse>>

}
