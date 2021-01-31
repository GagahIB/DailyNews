package id.gagahib.newsapi.ui.component.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.gagahib.newsapi.data.remote.model.CategoryModel
import id.gagahib.newsapi.databinding.CategoryItemBinding
import id.gagahib.newsapi.utils.setCustomFonts

class CategoryAdapter(private val categoryViewModel: CategoryViewModel, private val categories: List<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding, categoryViewModel)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(private val itemBinding: CategoryItemBinding, private val categoryViewModel: CategoryViewModel) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(categoryModel: CategoryModel) {
            itemBinding.tvCategoryName.text = categoryModel.name
            itemBinding.tvCategoryName.setCustomFonts()
            itemBinding.ivCategoryImage.setImageResource(categoryModel.icon)
            itemBinding.cvCategory.setOnClickListener {
                categoryViewModel.openNewsSource(categoryModel)
            }
        }
    }

}

