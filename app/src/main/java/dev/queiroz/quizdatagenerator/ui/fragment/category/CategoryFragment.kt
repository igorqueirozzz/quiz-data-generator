package dev.queiroz.quizdatagenerator.ui.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dev.queiroz.quizdatagenerator.activity.QuizViewModel
import dev.queiroz.quizdatagenerator.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    private val newQuizViewModel: QuizViewModel by activityViewModels()
    private val args by navArgs<CategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        newQuizViewModel.setCurrentFragmentName(args.categorySelected.description)
        return binding.root
    }





}