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
    childColumns = arrayOf("categoryId"),
    onDelete = ForeignKey.CASCADE
),
ForeignKey(
    entity = Quiz::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("quizId"),
    onDelete = ForeignKey.CASCADE
)])
data class Question(
    var description: String,
    var source: String,
    var answerList: AnswerList,
    val categoryId: Long,
    val quizId: Long): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
