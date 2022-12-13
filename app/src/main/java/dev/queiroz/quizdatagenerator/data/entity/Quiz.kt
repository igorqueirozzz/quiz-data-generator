package dev.queiroz.quizdatagenerator.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "quiz_table")
data class Quiz(val name: String): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
