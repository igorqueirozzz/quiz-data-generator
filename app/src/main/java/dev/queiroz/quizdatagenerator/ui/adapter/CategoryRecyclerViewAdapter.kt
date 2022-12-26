package dev.queiroz.quizdatagenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.data.entity.Question
import dev.queiroz.quizdatagenerator.ui.fragment.quiz.QuizFragmentDirections

class CategoryRecyclerViewAdapter() :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    private var dataSet: List<Category> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryNameTextView: TextView
        val categoryIconTextView: TextView
        val categoryRow: RelativeLayout

        init {
            categoryNameTextView = view.findViewById(R.id.tv_category_name)
            categoryIconTextView = view.findViewById(R.id.tv_icon_name)
            categoryRow = view.findViewById(R.id.category_row_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_categories_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryNameTextView.text = dataSet[position].name
        holder.categoryIconTextView.text = dataSet[position].icon
        holder.categoryRow.setOnClickListener {
            val action =
                QuizFragmentDirections.actionQuizFragmentToCategoryFragment(dataSet[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = dataSet.count()

    fun setData(dataSet: List<Category>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
    fun getItem(position: Int): Category = dataSet[position]
}