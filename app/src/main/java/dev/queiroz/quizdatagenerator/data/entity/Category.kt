package dev.queiroz.quizdatagenerator.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "category_table",
    foreignKeys = [ForeignKey(
        entity = Quiz::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("quiz")
    )]
)
data class Category(val description: String, var icon: String? = null, val quiz: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
