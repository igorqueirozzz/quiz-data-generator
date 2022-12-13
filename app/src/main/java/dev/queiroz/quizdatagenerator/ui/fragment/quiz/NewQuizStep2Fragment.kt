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
    private lateinit var categoryOptions: AutoCompleteTextView
    private lateinit var answerRecyclerView: RecyclerView
    private val newQuizViewModel: QuizViewModel by activityViewModels()
    private val listOfCategories by lazy {
        newQuizViewModel.categories.value
    }
    private var categorySelected: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewQuizStep2Binding.inflate(inflater, container, false)
        answerRecyclerView = binding.recyclerAnswers
        setCategoriesOptions()
        setListeners()
        setObservers()
        setupRecyclerView()
        return binding.root
    }

    private fun setCategoriesOptions() {
        val itemsLabel = listOfCategories?.map { category -> category.description }!!.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_item_option_list, itemsLabel)
        categoryOptions = ((binding.categoriesOptions as? AutoCompleteTextView)!!)
        categoryOptions.setAdapter(adapter)
        categoryOptions.setOnItemClickListener { adapterView, _, position, _ ->
            categorySelected =
                listOfCategories?.find {
                    it.description == adapterView.getItemAtPosition(position).toString()
                }
        }
    }

    private fun setListeners() {
        binding.run {
            buttonAddAnswer.setOnClickListener { validateAndAddAnswer() }
            buttonSaveQuestion.setOnClickListener { validateAndAddQuestion() }
        }
    }

    private fun setObservers() {
        newQuizViewModel.alertMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                newQuizViewModel.clearAlertMessage()
            }
        }
    }

    private fun validateAndAddAnswer() {
        val answerText = binding.tieAnswer.text.toString()
        val isCorrect = binding.cbIsCorrect.isChecked
        if (answerText.isNullOrBlank()) {
            Toast.makeText(context, "Answer field cannot be empty.", Toast.LENGTH_SHORT).show()
        } else {
            newQuizViewModel.addAnswer(
                Answer(
                    description = answerText,
                    isCorrect = isCorrect
                )
            )
            binding.run {
                tieAnswer.text = null
                cbIsCorrect.isChecked = false
            }
        }
    }

    private fun validateAndAddQuestion() {
        val questionText = binding.tieQuestion.text.toString()
        val questionSource = binding.tieAnswerSource.text.toString()

        if (questionText.isNullOrBlank()) {
            Toast.makeText(context, "Question field cannot be empty.", Toast.LENGTH_SHORT)
                .show()
        } else if (categorySelected == null) {
            Toast.makeText(
                context,
                "You must have to select some category.",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            newQuizViewModel.addQuestion(
                questionText = questionText,
                source = questionSource,
                category = categorySelected!!
            )
            binding.run {
                tieQuestion.text = null
                tieAnswerSource.text = null
            }
        }
    }

    private fun setupRecyclerView() {
        answerRecyclerView.layoutManager = LinearLayoutManager(context)
        answerRecyclerView.adapter = CategoryRecyclerViewAdapter(listOf())

//        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()){
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                newQuizViewModel.removeCategory(position)
//                answerRecyclerView.adapter?.notifyItemRemoved(position)
//            }
//        }

//        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
//        itemTouchHelper.attachToRecyclerView(categoriesRecyclerView)
//
        newQuizViewModel.answers.observe(viewLifecycleOwner) {
            with(answerRecyclerView) {
                setHasFixedSize(true)
                adapter = AnswerRecyclerViewAdapter(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }


}