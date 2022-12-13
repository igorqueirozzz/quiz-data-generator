package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.QuizViewModel
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.databinding.FragmentQuizBinding
import dev.queiroz.quizdatagenerator.ui.adapter.CategoryRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.util.extensions.isTextNullOrBlank
import dev.queiroz.quizdatagenerator.util.helper.SwipeToDeleteCallback

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var categoryNameEditText: EditText
    private lateinit var categoryIconEditText: EditText
    private lateinit var categoriesRecyclerView: RecyclerView

    private val newQuizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        with(view){
            categoryNameEditText = findViewById(R.id.tie_categories)
            categoryIconEditText = findViewById(R.id.tie_icon)
            categoriesRecyclerView = findViewById(R.id.recycler_categories)
        }
        //setAddCategoryButtonListener()
       // setupRecyclerView()
        return binding.root
    }

//    private fun setAddCategoryButtonListener() {
//        binding.run {
//            buttonAddCategory.run {
//                setOnClickListener {
//                    if (validateAddCategory()) {
//                        addNewCategory()
//                    }
//                }
//            }
//        }
//    }

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

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                newQuizViewModel.removeCategory(position)
                categoriesRecyclerView.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(categoriesRecyclerView)

        newQuizViewModel.categories.observe(viewLifecycleOwner) {
            with(categoriesRecyclerView) {
                setHasFixedSize(true)
                adapter = CategoryRecyclerViewAdapter(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }

}