package id.gagahib.newsapi.ui.component.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.gagahib.newsapi.R
import id.gagahib.newsapi.data.remote.model.ArticleModel
import id.gagahib.newsapi.data.remote.model.CountryModel
import id.gagahib.newsapi.data.remote.model.SourceModel
import id.gagahib.newsapi.databinding.ArticleItemBinding
import id.gagahib.newsapi.databinding.SourceItemBinding
import id.gagahib.newsapi.utils.MyConfig
import id.gagahib.newsapi.utils.glide.GlideApp

class ArticleAdapter(private val articleViewModel: ArticleViewModel,
                     private var articles: List<ArticleModel>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var sourceName: String = ""

    fun addItemData(itemList: List<ArticleModel>) {
        this.articles += itemList
        notifyDataSetChanged()
    }

    fun setItemData(itemList: List<ArticleModel>) {
        this.articles = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(itemBinding, articleViewModel)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position], sourceName)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(private val itemBinding: ArticleItemBinding,
                           private val articleViewModel: ArticleViewModel) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(articleModel: ArticleModel, sourceName: String) {
            itemBinding.tvArticleName.text = articleModel.title
            itemBinding.tvArticleDescription.text = articleModel.description
            itemBinding.tvArticleCategory.text = sourceName
            itemBinding.tvArticleTime.text = MyConfig.generateArticleTime(articleModel.publishedAt)

            GlideApp.with(itemView)
                .load(articleModel.urlToImage)
                .transition(DrawableTransitionOptions().crossFade())
                .placeholder(R.color.colorGray2)
                .into(itemBinding.ivArticleImage)

            itemBinding.root.setOnClickListener {
                articleViewModel.openArticleDetail(articleModel)
            }
        }
    }

}

