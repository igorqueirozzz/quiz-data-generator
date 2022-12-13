package dev.queiroz.quizdatagenerator.activity

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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepository: QuizRepository, private val categoryRepository: CategoryRepository) : ViewModel() {

    var categories: LiveData<List<Category>> = categoryRepository.findAllByQuiz(0)
    val quizList: LiveData<List<Quiz>> = quizRepository.readAllData

    private val _currentFragmentName: MutableLiveData<String> = MutableLiveData("Home")
    val currentFragmentName = _currentFragmentName

    var quiz: Quiz = Quiz("")

    fun addQuiz(quizName: String){
        viewModelScope.launch(Dispatchers.IO){
            quizRepository.addQuiz(Quiz(name = quizName))
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO){
            categoryRepository.addCategory(category)
        }
    }

    fun removeCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO){
            categoryRepository.deleteCategory(category)
        }
    }


    fun setCurrentQuiz(quiz: Quiz){
        this.quiz = quiz
        categories = categoryRepository.findAllByQuiz(quiz.id)
    }

    fun setCurrentFragmentName(fragmentName: String){
        _currentFragmentName.value = fragmentName
    }

}