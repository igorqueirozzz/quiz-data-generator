package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentQuizBinding
import dev.queiroz.quizdatagenerator.ui.adapter.CategoryRecyclerViewAdapter

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var categoriesRecyclerView: RecyclerView

    private val quizViewModel: QuizViewModel by activityViewModels()
    private val args by navArgs<QuizFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        with(view){
            categoriesRecyclerView = findViewById(R.id.recycler_categories)
        }
        quizViewModel.setCurrentQuiz(args.currentQuiz)
        quizViewModel.setCurrentFragmentName(args.currentQuiz.name)
        setupRecyclerView()
        return binding.root
    }


    private fun setupRecyclerView() {
        val adapter = CategoryRecyclerViewAdapter()
        categoriesRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            setAdapter(adapter)
            setHasFixedSize(true)
        }

        quizViewModel.categories.observe(viewLifecycleOwner) {
               adapter.setData(it)
        }
    }

}