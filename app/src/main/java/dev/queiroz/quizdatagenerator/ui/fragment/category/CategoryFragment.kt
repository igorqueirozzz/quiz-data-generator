package dev.queiroz.quizdatagenerator.ui.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.activity.viewmodel.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentCategoryBinding
import dev.queiroz.quizdatagenerator.ui.adapter.QuestionRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.util.helper.SwipeToDeleteCallback

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var questionRecyclerView: RecyclerView
    private val quizViewModel: QuizViewModel by activityViewModels()
    private val args by navArgs<CategoryFragmentArgs>()
    private val adapter = QuestionRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        questionRecyclerView = binding.questionRecycler
        quizViewModel.setCurrentFragmentName(args.categorySelected.name)
        quizViewModel.setCurrentCategory(args.categorySelected)
        setupRecyclerView()
        setupObservers()
        return binding.root
    }

    private fun setupRecyclerView(){
        questionRecyclerView.setHasFixedSize(true)
        questionRecyclerView.layoutManager = LinearLayoutManager(context)
        questionRecyclerView.adapter = adapter

        val swipeToDelete = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val question = adapter.getItem(position)
                quizViewModel.deleteQuestion(question = question)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(questionRecyclerView)
    }

    private fun setupObservers(){
        quizViewModel.questions.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
}