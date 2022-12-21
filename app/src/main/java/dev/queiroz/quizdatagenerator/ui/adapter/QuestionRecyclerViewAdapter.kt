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
import dev.queiroz.quizdatagenerator.ui.fragment.category.CategoryFragmentDirections
import dev.queiroz.quizdatagenerator.ui.fragment.quiz.QuizFragmentDirections

class QuestionRecyclerViewAdapter() :
    RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {

    private var dataSet: List<Question> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionDescription: TextView
        val numberOfAnswers: TextView
        val questionRow: RelativeLayout

        init {
            questionDescription = view.findViewById(R.id.tv_question_description)
            numberOfAnswers = view.findViewById(R.id.tv_number_of_answers)
            questionRow = view.findViewById(R.id.question_row_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_question_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionDescription.text = dataSet[position].questionText
        holder.numberOfAnswers.text = dataSet[position].answerList.answers.count().toString()
        holder.questionRow.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToQuestionFragment().setQuestionOnEdition(dataSet[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = dataSet.count()

    fun setData(dataSet: List<Question>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}