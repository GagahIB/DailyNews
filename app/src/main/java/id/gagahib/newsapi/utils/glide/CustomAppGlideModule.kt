package id.gagahib.newsapi.utils.glide

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

import java.io.InputStream

@GlideModule
class CustomAppGlideModule : AppGlideModule() {

    /**
     *
     *
     * @param context
     * @param builder
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {

        //重新设置内存限制
        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))

    }


    /**
     *
     * @return
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    /**
     *
     * @param context
     * @param glide
     * @param registry
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(String::class.java, InputStream::class.java, CustomBaseGlideUrlLoader.Factory())
    }
}
