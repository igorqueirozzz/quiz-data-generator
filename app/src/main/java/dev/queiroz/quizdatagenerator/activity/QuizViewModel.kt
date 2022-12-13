package dev.queiroz.quizdatagenerator.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.queiroz.quizdatagenerator.data.entity.*
import dev.queiroz.quizdatagenerator.data.repository.QuestionRepository
import dev.queiroz.quizdatagenerator.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepository: QuizRepository) : ViewModel() {
    private val _categories = MutableLiveData(mutableListOf<Category>())
    val categories: LiveData<MutableList<Category>> = _categories

    private val _questions = MutableLiveData(mutableListOf<Question>())
    val questions: LiveData<MutableList<Question>> = _questions

    private val _answers = MutableLiveData(mutableListOf<Answer>())
    val answers: LiveData<MutableList<Answer>> = _answers

    private val _alertMessage = MutableLiveData<String>()
    val alertMessage: LiveData<String> = _alertMessage

    val quizList: LiveData<List<Quiz>> = quizRepository.readAllData

    fun addQuiz(quizName: String){
        viewModelScope.launch(Dispatchers.IO){
            quizRepository.addQuiz(Quiz(name = quizName))
        }
       // _alertMessage.value = "Quiz $quizName successfully added."
    }

    fun addCategory(category: Category) {
        val list = categories.value
        list?.add(category)
        _categories.value = list
    }

    fun removeCategory(index: Int) {
        val list = categories.value
        list?.removeAt(index)
        _categories.value = list
    }

    fun addAnswer(answer: Answer) {
        val list = answers.value
        list?.add(answer)
        _answers.value = list
    }

    fun addQuestion(questionText: String, source: String, category: Category) {
        if (answers.value!!.isEmpty()) {
            _alertMessage.value = "You must add some answer."
        } else {
            val list = _questions.value
            list?.add(
                Question(
                    categoryId = category.id,
                    questionText = questionText,
                    source = source,
                    answerList = AnswerList(answers.value!!)
                )
            )
            _questions.value = list
        }

    }

    fun clearAlertMessage(){
        _alertMessage.value = ""
    }

}