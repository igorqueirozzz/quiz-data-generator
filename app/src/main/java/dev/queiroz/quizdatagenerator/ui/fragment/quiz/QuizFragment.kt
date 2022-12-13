package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.MainActivity
import dev.queiroz.quizdatagenerator.activity.QuizViewModel
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.data.entity.Quiz
import dev.queiroz.quizdatagenerator.databinding.FragmentQuizBinding
import dev.queiroz.quizdatagenerator.ui.adapter.CategoryRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.util.extensions.isTextNullOrBlank
import dev.queiroz.quizdatagenerator.util.helper.SwipeToDeleteCallback

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var categoryNameEditText: EditText
    private lateinit var categoryIconEditText: EditText
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

        //setAddCategoryButtonListener()
        setupRecyclerView()
        return binding.root
    }


    private fun validateAddCategory(): Boolean {
        val isValid = !categoryNameEditText.isTextNullOrBlank()
        if (!isValid) {
            Toast.makeText(
                context,
                getString(R.string.category_must_be_informed),
                Toast.LENGTH_SHORT
            ).show()
        }
        return isValid
    }

    private fun addNewCategory() {
        val categoryName = categoryNameEditText.text.toString()
        val categoryIconName = categoryIconEditText.text.toString()
        val category = Category(description = categoryName, icon = categoryIconName, 0L)
        quizViewModel.addCategory(category)
        categoryNameEditText.text = null
        categoryIconEditText.text = null
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
            Toast.makeText(context, "${it.size}", Toast.LENGTH_SHORT).show()
        }
    }

}