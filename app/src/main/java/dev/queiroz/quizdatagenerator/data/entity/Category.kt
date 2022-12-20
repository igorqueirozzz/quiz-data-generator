package dev.queiroz.quizdatagenerator.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "category_table",
    foreignKeys = [ForeignKey(
        entity = Quiz::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("quiz")
    )]
)
data class Category(val description: String, var icon: String? = null, val quiz: Long) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
