package id.gagahib.newsapi.data.remote

import id.gagahib.newsapi.data.local.LocalData
import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.mylogin.data.remote.payload.NewsPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import id.gagahib.core.remote.Resource

class RemoteDataRepository @Inject
constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData) : DataRepositorySource {

    override suspend fun getSourcesByCategory(category: String): Flow<Resource<NewsResponse>>{
        return flow {
            emit(remoteRepository.getSourcesByCategory(category))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNewsBySources(
        sources: String,
        pageSize: Int,
        page: Int,
        q: String
    ): Flow<Resource<NewsResponse>> {
        return flow {
            emit(remoteRepository.getNewsBySources(
                sources, pageSize, page, q
            ))
        }.flowOn(Dispatchers.IO)
    }
}
