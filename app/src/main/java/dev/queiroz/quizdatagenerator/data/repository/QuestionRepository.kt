package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.entity.Question

interface QuestionRepository {
    fun getAllData():LiveData<List<Question>>
}