package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentNewQuizStep2Binding
import dev.queiroz.quizdatagenerator.data.entity.Answer
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.ui.adapter.AnswerRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.ui.adapter.CategoryRecyclerViewAdapter

class NewQuizStep2Fragment : Fragment() {
    private lateinit var binding: FragmentNewQuizStep2Binding

    private val newQuizViewModel: QuizViewModel by activityViewModels()

    private var categorySelected: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewQuizStep2Binding.inflate(inflater, container, false)

        return binding.root
    }





}