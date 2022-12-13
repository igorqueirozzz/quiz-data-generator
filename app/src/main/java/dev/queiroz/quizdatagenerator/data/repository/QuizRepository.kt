package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.entity.Quiz

interface QuizRepository {
    val readAllData: LiveData<List<Quiz>>
    suspend fun addQuiz(quiz: Quiz)
    suspend fun updateQuiz(quiz: Quiz)
    suspend fun deleteQuiz(quiz: Quiz)
}