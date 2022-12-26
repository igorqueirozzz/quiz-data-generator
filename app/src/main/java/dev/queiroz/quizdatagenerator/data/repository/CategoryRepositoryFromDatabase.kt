package dev.queiroz.quizdatagenerator.data.repository

import androidx.lifecycle.LiveData
import dev.queiroz.quizdatagenerator.data.dao.CategoryDAO
import dev.queiroz.quizdatagenerator.data.entity.Category
import javax.inject.Inject

class CategoryRepositoryFromDatabase @Inject constructor(private val categoryDAO: CategoryDAO) : CategoryRepository {

    override fun findAllByQuizLiveData(quizId: Long): LiveData<List<Category>> = categoryDAO.findAllByQuizLiveData(quizId)
    override fun findAllByQuiz(quizId: Long): List<Category> = categoryDAO.findAllByQuiz(quizId)

    override suspend fun addCategory(category: Category) {
        categoryDAO.insert(category)
    }

    override suspend fun updateCategory(category: Category) {
        categoryDAO.update(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDAO.delete(category)
    }

}