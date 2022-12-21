package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.entity.Question

interface QuestionRepository {
    suspend fun addQuestion(question: Question)
    fun getAllDataByQuiz(quizId: Long):LiveData<List<Question>>
    fun findByCategoryId(categoryId: Long):LiveData<List<Question>>
    suspend fun updateQuestion(question: Question)
}