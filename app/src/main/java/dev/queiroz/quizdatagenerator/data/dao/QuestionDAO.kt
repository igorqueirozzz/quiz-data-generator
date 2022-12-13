package dev.queiroz.quizdatagenerator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.queiroz.quizdatagenerator.data.entity.Question

@Dao
interface QuestionDAO : BaseDao<Question>{
    @Query("SELECT * FROM question_table")
    fun readAllData():LiveData<List<Question>>
}