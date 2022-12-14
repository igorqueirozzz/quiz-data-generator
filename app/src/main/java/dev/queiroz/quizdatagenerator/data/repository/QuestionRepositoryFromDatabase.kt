package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.dao.QuestionDAO
import dev.queiroz.quizdatagenerator.data.entity.Question
import javax.inject.Inject

class QuestionRepositoryFromDatabase @Inject constructor(private val questionDAO: QuestionDAO) :
    QuestionRepository {
    override fun findAllByQuiz(quizId: Long): List<Question> = questionDAO.findAllByQuiz(quizId)

    override fun findByCategoryIdLiveData(categoryId: Long): LiveData<List<Question>> =
        questionDAO.findByCategoryId(categoryId)

    override suspend fun addQuestion(question: Question) = questionDAO.insert(question)

    override suspend fun updateQuestion(question: Question) = questionDAO.update(question)

    override suspend fun deleteQuestion(question: Question) = questionDAO.delete(question)
}