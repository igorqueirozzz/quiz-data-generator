package dev.queiroz.quizdatagenerator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_table")
data class Quiz(val name: String){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
