package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.entity.Question

interface QuestionRepository {
    suspend fun addQuestion(question: Question)
    fun findAllByQuiz(quizId: Long):List<Question>
    fun findByCategoryIdLiveData(categoryId: Long):LiveData<List<Question>>
    suspend fun updateQuestion(question: Question)
    suspend fun deleteQuestion(question: Question)
}