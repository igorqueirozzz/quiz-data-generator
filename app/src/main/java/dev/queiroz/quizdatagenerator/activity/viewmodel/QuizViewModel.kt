package dev.queiroz.quizdatagenerator.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.queiroz.quizdatagenerator.data.entity.*
import dev.queiroz.quizdatagenerator.data.repository.CategoryRepository
import dev.queiroz.quizdatagenerator.data.repository.QuestionRepository
import dev.queiroz.quizdatagenerator.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val categoryRepository: CategoryRepository,
    private val questionRepository: QuestionRepository
) : ViewModel() {

    var categories: LiveData<List<Category>> = categoryRepository.findAllByQuiz(0)
    var questions: LiveData<List<Question>> = questionRepository.findByCategoryId(0L)
    val quizList: LiveData<List<Quiz>> = quizRepository.readAllData

    private val _currentFragmentName: MutableLiveData<String> = MutableLiveData("Home")
    val currentFragmentName = _currentFragmentName

    private val _answers: MutableLiveData<MutableList<Answer>> = MutableLiveData(mutableListOf())
    val answers: LiveData<MutableList<Answer>> = _answers

    var quiz: Quiz = Quiz("")
    var category: Category = Category("", null, 0L)

    fun addQuiz(quizName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.addQuiz(Quiz(name = quizName))
        }


    fun addCategory(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.addCategory(category)
        }


    fun addQuestion(questionText: String, source: String) =
        viewModelScope.launch(Dispatchers.IO) {
            questionRepository.addQuestion(
                Question(
                    description = questionText,
                    source = source,
                    answerList = AnswerList(answers.value!!.toList()),
                    categoryId = category.id,
                    quizId = quiz.id
                )
            )
        }


    fun updateQuestion(question: Question) {
        question.answerList.answers = _answers.value!!.toList()
        viewModelScope.launch(Dispatchers.IO) {
            questionRepository.updateQuestion(question)
        }
    }


    fun deleteQuestion(question: Question) =
        viewModelScope.launch(Dispatchers.IO) { questionRepository.deleteQuestion(question) }


    fun addAnswer(answer: String) {
        val list = _answers.value
        list?.add(Answer(description = answer, false))
        _answers.value = list!!
    }

    fun addAnswer(answer: Answer) {
        val list = _answers.value
        list?.add(answer)
        _answers.value = list!!
    }

    fun clearAnswerList() {
        _answers.value = mutableListOf()
    }

    fun deleteCategory(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.deleteCategory(category)
        }


    fun updateAnswerCorrect(updatedAnswer: Answer) {
        answers.value!!.find { it.description == updatedAnswer.description }?.isCorrect =
            updatedAnswer.isCorrect
    }

    fun removeAnswerFromAnswersCreateList(answer: Answer) {
        val list = _answers.value
        list?.remove(answer)
        _answers.value = list ?: mutableListOf()
    }

    fun setCurrentQuiz(quiz: Quiz) {
        this.quiz = quiz
        categories = categoryRepository.findAllByQuiz(quizId = quiz.id)
    }

    fun setCurrentFragmentName(fragmentName: String) {
        _currentFragmentName.value = fragmentName
    }

    fun setCurrentCategory(category: Category) {
        this.category = category
        questions = questionRepository.findByCategoryId(categoryId = category.id)
    }

}