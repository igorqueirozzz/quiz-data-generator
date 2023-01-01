package dev.queiroz.quizdatagenerator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.queiroz.quizdatagenerator.data.entity.Category
@Dao
interface CategoryDAO : BaseDao<Category> {
    @Query("SELECT * FROM category_table WHERE quizId = :quizId")
    fun findAllByQuizLiveData(quizId: Long): LiveData<List<Category>>

    @Query("SELECT * FROM category_table WHERE quizId = :quizId")
    fun findAllByQuiz(quizId: Long): List<Category>
}