package id.gagahib.newsapi.ui.component.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.gagahib.newsapi.data.remote.model.CountryModel
import id.gagahib.newsapi.data.remote.model.SourceModel
import id.gagahib.newsapi.databinding.SourceItemBinding

class SourceAdapter(private val sourceViewModel: SourceViewModel,
                    private var sources: List<SourceModel>) : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    var countries: List<CountryModel> = emptyList()

    fun setItemData(itemList: List<SourceModel>) {
        this.sources = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val itemBinding = SourceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(itemBinding, sourceViewModel, countries)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sources[position], position)
    }

    override fun getItemCount(): Int {
        return sources.size
    }

    class SourceViewHolder(private val itemBinding: SourceItemBinding,
                           private val sourceViewModel: SourceViewModel,
                           private val countries: List<CountryModel>) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(sourceModel: SourceModel, position: Int) {
            itemBinding.tvSourceIcon.text = sourceModel.name.subSequence(0,1)
            itemBinding.tvSourceName.text = sourceModel.name
            itemBinding.tvSourceSite.text = sourceModel.url
            itemBinding.tvSourceCountry.text = sourceModel.country

            for (i in countries.indices) {
                if (countries[i].code.equals(sourceModel.country, true)){
                    itemBinding.tvSourceCountry.text = countries[i].name
                    break
                }
            }

            itemBinding.root.setOnClickListener {
                sourceViewModel.openSourceDetail(sourceModel)
            }
        }
    }

}

