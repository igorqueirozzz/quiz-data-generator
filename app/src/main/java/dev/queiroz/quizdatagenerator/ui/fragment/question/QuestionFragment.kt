package dev.queiroz.quizdatagenerator.ui.fragment.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.queiroz.quizdatagenerator.activity.MainActivity
import dev.queiroz.quizdatagenerator.activity.QuizViewModel
import dev.queiroz.quizdatagenerator.data.entity.Answer
import dev.queiroz.quizdatagenerator.data.entity.AnswerList
import dev.queiroz.quizdatagenerator.databinding.FragmentQuestionBinding
import dev.queiroz.quizdatagenerator.ui.adapter.AnswerRecyclerViewAdapter
import dev.queiroz.quizdatagenerator.ui.adapter.OnItemUpdate
import dev.queiroz.quizdatagenerator.util.helper.SwipeToDeleteCallback

class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var answerRecyclerView: RecyclerView
    private lateinit var answerEditText: EditText
    private lateinit var questionEditText: EditText
    private lateinit var sourceEditText: EditText
    private val adapter = AnswerRecyclerViewAdapter()
    private val quizViewModel: QuizViewModel by activityViewModels()
    private val args by navArgs<QuestionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        binding.tieCategory.setText(quizViewModel.category.description)
        quizViewModel.setCurrentFragmentName(quizViewModel.category.description)
        answerRecyclerView = binding.answersRecycler
        answerEditText = binding.tieAnswer
        questionEditText = binding.tieQuestion
        sourceEditText = binding.tieSource
        (activity as MainActivity).hideBottomBar()
        checkArgs()
        setupRecyclerView()
        setupObservers()
        setupListeners()
        return binding.root
    }

    override fun onPause() {
        if (activity is MainActivity) {
            (activity as MainActivity).showBottomBar()
        }
        quizViewModel.clearAnswerList()
        super.onPause()
    }

    private fun setupRecyclerView() {
        adapter.setOnItemUpdateListener(object : OnItemUpdate {
            override fun onUpdated(answer: Answer) {
                quizViewModel.updateAnswerCorrect(answer)
            }

        })
        answerRecyclerView.setHasFixedSize(true)
        answerRecyclerView.layoutManager = LinearLayoutManager(context)
        answerRecyclerView.adapter = adapter

        val swipeToDelete = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val answer = adapter.getItem(position)
                quizViewModel.removeAnswerFromAnswersCreateList(answer)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(answerRecyclerView)
    }

    private fun checkArgs() {
        if (args.questionOnEdition != null) {
            with(args.questionOnEdition) {
                questionEditText.setText(this!!.questionText)
                sourceEditText.setText(this!!.source)
                this.answerList.answers.forEach { quizViewModel.addAnswer(it) }
            }
        }
    }

    private fun setupObservers() {
        quizViewModel.answers.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun setupListeners() {
        binding.run {
            btnAddAnswer.setOnClickListener { addAnswer() }
            fabSaveQuestion.setOnClickListener { if (args.questionOnEdition != null) addQuestion() else updateQuestion() }
        }
    }

    private fun addQuestion() {
        if (validateAddQuestion()) {
            quizViewModel.addQuestion(
                questionText = questionEditText.text.toString(),
                source = sourceEditText.text.toString()
            )
            findNavController().navigateUp()
        }
    }

    private fun updateQuestion() {
        if (validateAddQuestion()) {
            val question = args.questionOnEdition!!
            question.questionText = questionEditText.text.toString()
            question.source = sourceEditText.text.toString()
            question.answerList = AnswerList(quizViewModel.answers.value!!.toList())
            quizViewModel.updateQuestion(question)
        }
    }

    private fun addAnswer() {
        if (validateAddAnswer()) {
            quizViewModel.addAnswer(answer = answerEditText.text.toString())
            answerEditText.text = null

        }
    }

    private fun validateAddQuestion(): Boolean {
        if (questionEditText.text.toString().isNullOrBlank()) {
            Toast.makeText(context, "Question can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        } else if (quizViewModel.answers.value?.isEmpty() == true) {
            Toast.makeText(context, "Answers list can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        } else if (quizViewModel.answers.value!!.count { it.isCorrect } != 1) {
            Toast.makeText(
                context,
                "You must to provide just one correct answer.",
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        }
        return true
    }

    private fun validateAddAnswer(): Boolean {
        if (answerEditText.text.toString().isNullOrBlank()) {
            Toast.makeText(context, "Answer can't be empty.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}