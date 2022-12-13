package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.dao.QuestionDAO
import dev.queiroz.quizdatagenerator.data.entity.Question
import javax.inject.Inject

class QuestionRepositoryDatabase @Inject constructor(private val questionDAO: QuestionDAO) : QuestionRepository {
    override fun getAllData(): LiveData<List<Question>> = questionDAO.readAllData()
}