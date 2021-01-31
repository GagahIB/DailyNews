package id.gagahib.newsapi.data.error

import javax.inject.Inject
import id.gagahib.core.data.error.ErrorFactory
import id.gagahib.core.data.error.Error

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorFactory {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }

}