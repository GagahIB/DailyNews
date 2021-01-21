package id.gagahib.newsapi.utils

import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel

class Constants {
    companion object INSTANCE {
        const val SPLASH_DELAY = 3000
        const val BASE_URL = "http://newsapi.org/v2/"
        const val BUNDLE_CATEGORY = "CATEGORY"
        const val BUNDLE_SOURCE = "SOURCE"
        const val BUNDLE_ARTICLE = "ARTICLE"

        val categories = arrayListOf(
            CategoryModel("", "All", R.drawable.ic_all),
            CategoryModel("business", "Business", R.drawable.ic_business),
            CategoryModel("entertainment", "Entertainment", R.drawable.ic_entertainment),
            CategoryModel("general", "General", R.drawable.ic_general),
            CategoryModel("health", "Health", R.drawable.ic_health),
            CategoryModel("science", "Science", R.drawable.ic_science),
            CategoryModel("sports", "Sports", R.drawable.ic_sports),
            CategoryModel("technology", "Technology", R.drawable.ic_technology)
        )
    }
}
