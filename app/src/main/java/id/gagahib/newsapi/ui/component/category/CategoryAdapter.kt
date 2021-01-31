package id.gagahib.newsapi.ui.component.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.CategoryItemBinding
import id.gagahib.newsapi.utils.setCustomFonts

class CategoryAdapter(private val mainViewModel: MainViewModel, private val categories: List<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding, mainViewModel)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(private val itemBinding: CategoryItemBinding, private val mainViewModel: MainViewModel) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(categoryModel: CategoryModel) {
            itemBinding.tvCategoryName.text = categoryModel.name
            itemBinding.tvCategoryName.setCustomFonts()
            itemBinding.ivCategoryImage.setImageResource(categoryModel.icon)

            itemBinding.cvCategory.setOnClickListener {
                mainViewModel.openNewsSource(categoryModel)
            }
        }
    }

}

