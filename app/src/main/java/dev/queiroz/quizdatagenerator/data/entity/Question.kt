package dev.queiroz.quizdatagenerator.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    val categoryId: Long,
    val questionText: String,
    val source: String,
    val answerList: AnswerList){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
