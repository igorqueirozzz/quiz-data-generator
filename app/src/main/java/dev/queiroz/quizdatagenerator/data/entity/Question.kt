package dev.queiroz.quizdatagenerator.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var questionText: String,
    var source: String,
    var answerList: AnswerList,
    val categoryId: Long,
    val quizId: Long): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
