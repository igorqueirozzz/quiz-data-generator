package dev.queiroz.quizdatagenerator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.queiroz.quizdatagenerator.data.entity.Quiz

@Dao
interface QuizDAO : BaseDao<Quiz> {

    @Query("SELECT * FROM quiz_table")
    fun readAllData():LiveData<List<Quiz>>

    @Query("SELECT * FROM quiz_table WHERE id = :id")
    fun findById(id: Long): Quiz
}