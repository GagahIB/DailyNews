package id.gagahib.newsapi.utils

import android.util.Log
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.CategoryModel
import java.text.SimpleDateFormat
import java.util.*

class MyConfig {
    companion object INSTANCE {

        fun generateArticleTime(time: String): String{
            var parseTime: String = time
            try {

                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                val mDate = formatter.parse(time)

                val newFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                parseTime =  newFormatter.format(mDate)
            } catch (e: Exception){
                Log.e("mDate",e.toString())
            }
            return parseTime
        }
    }
}
