package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.dao.QuizDAO
import dev.queiroz.quizdatagenerator.data.entity.Quiz
import javax.inject.Inject

class QuizRepositoryFromDatabase @Inject constructor(private val quizDAO: QuizDAO) : QuizRepository {
    override val readAllData: LiveData<List<Quiz>> = quizDAO.readAllData()
    override suspend fun addQuiz(quiz: Quiz) = quizDAO.insert(quiz)
    override suspend fun updateQuiz(quiz: Quiz) = quizDAO.update(quiz)
    override suspend fun deleteQuiz(quiz: Quiz) = quizDAO.delete(quiz)
}