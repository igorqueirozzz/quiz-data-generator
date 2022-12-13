package dev.queiroz.quizdatagenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.data.entity.Quiz
import dev.queiroz.quizdatagenerator.ui.fragment.home.HomeFragment
import dev.queiroz.quizdatagenerator.ui.fragment.home.HomeFragmentDirections

class QuizRecyclerViewAdapter() :
    RecyclerView.Adapter<QuizRecyclerViewAdapter.ViewHolder>() {

    private var dataSet: List<Quiz> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val quizNameTextView: TextView
        val quizRowLayout: RelativeLayout
        init {
            quizNameTextView = view.findViewById(R.id.tv_quiz_name)
            quizRowLayout = view.findViewById(R.id.quiz_row_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_quiz_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.quizNameTextView.text = dataSet[position].name
        holder.quizRowLayout.setOnClickListener{
            val action = HomeFragmentDirections.actionFromHomeToQuizFragment(dataSet[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = dataSet.count()

    fun setData(dataSet: List<Quiz>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}