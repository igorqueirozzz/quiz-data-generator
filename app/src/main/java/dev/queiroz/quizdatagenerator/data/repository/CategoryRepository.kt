package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.entity.Category

interface CategoryRepository {
    fun findAllByQuizLiveData(quizId: Long): LiveData<List<Category>>
    fun findAllByQuiz(quizId: Long): List<Category>
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}