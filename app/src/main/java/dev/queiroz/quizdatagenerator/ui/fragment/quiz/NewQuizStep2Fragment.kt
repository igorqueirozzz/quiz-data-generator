package dev.queiroz.quizdatagenerator.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.queiroz.quizdatagenerator.R
import dev.queiroz.quizdatagenerator.activity.NewQuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentNewQuizStep2Binding

class NewQuizStep2Fragment : Fragment() {
    private lateinit var binding: FragmentNewQuizStep2Binding
    private lateinit var categoryOptions: AutoCompleteTextView
    private val newQuizViewModel: NewQuizViewModel by activityViewModels()
    private val listOfCategories by lazy {
        newQuizViewModel.categories.value
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewQuizStep2Binding.inflate(inflater, container, false)
        setCategoriesOptions()
        return binding.root
    }

    private fun setCategoriesOptions(){
        val itemsLabel = listOfCategories?.map { category -> category.description }!!.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_item_option_list, itemsLabel)
        categoryOptions = ((binding.categoriesOptions as? AutoCompleteTextView)!!)
        categoryOptions.setAdapter(adapter)
    }


}