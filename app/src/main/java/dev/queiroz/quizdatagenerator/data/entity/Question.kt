package dev.queiroz.quizdatagenerator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "question_table", foreignKeys = [ForeignKey(
    entity = Category::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("categoryId")
),
ForeignKey(
    entity = Quiz::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("quizId")
)])
data class Question(
    val questionText: String,
    val source: String,
    val answerList: AnswerList,
    val categoryId: Long,
    val quizId: Long){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
