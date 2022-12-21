package dev.queiroz.quizdatagenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.data.entity.Answer

class AnswerRecyclerViewAdapter :
    RecyclerView.Adapter<AnswerRecyclerViewAdapter.ViewHolder>() {

    private var dataSet: List<Answer> = listOf()
    private var onItemUpdate: OnItemUpdate? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerIndex: TextView
        val answerText: TextView
        val checkboxIsCorrect: CheckBox

        init {
            answerText = view.findViewById(R.id.tv_answer)
            answerIndex = view.findViewById(R.id.tv_answer_index)
            checkboxIsCorrect = view.findViewById(R.id.cb_is_answer_correct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_answer_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.answerIndex.text = "${position + 1})"
        holder.answerText.text = dataSet[position].description
        holder.checkboxIsCorrect.isChecked = dataSet[position].isCorrect
        holder.checkboxIsCorrect.setOnCheckedChangeListener { compoundButton, _ ->
            dataSet[position].isCorrect = compoundButton.isChecked
            onItemUpdate?.onUpdated(
                answer = dataSet[position]
            )
        }
    }

    override fun getItemCount() = dataSet.count()

    fun setData(dataSet: List<Answer>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Answer{
        return dataSet[position]
    }

    fun setOnItemUpdateListener(onItemUpdate: OnItemUpdate) {
        this.onItemUpdate = onItemUpdate
    }
}