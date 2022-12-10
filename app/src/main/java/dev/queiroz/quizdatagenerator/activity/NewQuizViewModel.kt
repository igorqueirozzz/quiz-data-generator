package dev.queiroz.quizdatagenerator.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.queiroz.quizdatagenerator.model.Answer
import dev.queiroz.quizdatagenerator.model.Category
import dev.queiroz.quizdatagenerator.model.Question
import javax.inject.Inject

@HiltViewModel
class NewQuizViewModel @Inject constructor() : ViewModel() {
    private val _stepper = MutableLiveData(0)
    val stepper: LiveData<Int> = _stepper

    private val _categories = MutableLiveData(mutableListOf<Category>())
    val categories: LiveData<MutableList<Category>> = _categories

    private val _questions = MutableLiveData(mutableListOf<Question>())
    val questions: LiveData<MutableList<Question>> = _questions

    private val _answers = MutableLiveData(mutableListOf<Answer>())
    val answers: LiveData<MutableList<Answer>> = _answers

    private val _alertMessage = MutableLiveData<String>()
    val alertMessage: LiveData<String> = _alertMessage

    fun nextStep() {
        if (_stepper.value == 0) _stepper.value = (_stepper.value!! + 1)
    }

    fun backStep() {
        _stepper.value = (_stepper.value!! - 1)
        if (_stepper.value!! < 0) _stepper.value = 0
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
                    id = null,
                    category = category,
                    questionText = questionText,
                    source = source,
                    answers = answers.value!!
                )
            )
            _questions.value = list
        }

    }

    fun clearAlertMessage(){
        _alertMessage.value = ""
    }

}