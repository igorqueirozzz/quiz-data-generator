package dev.queiroz.quizdatagenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.data.entity.Category

class CategoryRecyclerViewAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryNameTextView: TextView
        val categoryIconTextView: TextView

        init {
            categoryNameTextView = view.findViewById(R.id.tv_category_name)
            categoryIconTextView = view.findViewById(R.id.tv_icon_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_categories_list, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryNameTextView.text = dataSet[position].description
        holder.categoryIconTextView.text = dataSet[position].icon
    }

    override fun getItemCount() = dataSet.count()
}