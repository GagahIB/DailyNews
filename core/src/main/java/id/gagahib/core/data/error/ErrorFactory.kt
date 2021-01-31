package id.gagahib.core.data.error


interface ErrorFactory {
    fun getError(errorCode: Int): Error
}