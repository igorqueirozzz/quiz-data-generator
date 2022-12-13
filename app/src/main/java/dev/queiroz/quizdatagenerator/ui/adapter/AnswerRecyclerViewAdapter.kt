package dev.queiroz.quizdatagenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.data.entity.Answer

class AnswerRecyclerViewAdapter(private val dataSet: List<Answer>) :
    RecyclerView.Adapter<AnswerRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerText: TextView
        val isCorrectText: TextView

        init {
            answerText = view.findViewById(R.id.tv_answer)
            isCorrectText = view.findViewById(R.id.tv_is_correct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_answer_list, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.answerText.text = dataSet[position].description
        holder.isCorrectText.run{
            if(dataSet[position].isCorrect){
                text = context.getString(R.string.correct)
                setTextColor(resources.getColor(R.color.green))
            }else {
                text = context.getString(R.string.Incorrect)
                setTextColor(resources.getColor(R.color.red))
            }
        }
    }

    override fun getItemCount() = dataSet.count()
}