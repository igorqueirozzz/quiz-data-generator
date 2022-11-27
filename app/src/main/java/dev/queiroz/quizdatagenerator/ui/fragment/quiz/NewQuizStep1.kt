package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.NewQuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentNewQuizStep1Binding
import dev.queiroz.quizdatagenerator.model.Category
import dev.queiroz.quizdatagenerator.ui.fragment.quiz.adapter.CategoryRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.util.isTextNullOrBlank
import kotlinx.android.synthetic.main.fragment_new_quiz_step1.view.*

class NewQuizStep1 : Fragment() {
    private lateinit var binding: FragmentNewQuizStep1Binding
    private lateinit var categoryNameEditText: EditText
    private lateinit var categoryIconEditText: EditText
    private lateinit var categoriesRecyclerView: RecyclerView

    private lateinit var newQuizViewModel: NewQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewQuizStep1Binding.inflate(inflater, container, false)
        categoryNameEditText = binding.root.tie_categories
        categoryIconEditText = binding.root.tie_icon
        categoriesRecyclerView = binding.root.recycler_categories
        newQuizViewModel = ViewModelProvider(requireActivity())[NewQuizViewModel::class.java]
        setAddCategoryButtonListener()
        setupRecyclerView()
        return binding.root
    }

    private fun setAddCategoryButtonListener() {
        binding.run {
            buttonAddCategory.run {
                setOnClickListener {
                    if (validateAddCategory()) {
                        addNewCategory()
                    }
                }
            }
        }
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
        val category = Category(description = categoryName, icon = categoryIconName)
        newQuizViewModel.addCategory(category)
        categoryNameEditText.text = null
        categoryIconEditText.text = null
    }

    private fun setupRecyclerView() {
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        categoriesRecyclerView.adapter = CategoryRecyclerViewAdapter(listOf())
        newQuizViewModel.categories.observe(viewLifecycleOwner) {
            categoriesRecyclerView.adapter = CategoryRecyclerViewAdapter(it)
            categoriesRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

}