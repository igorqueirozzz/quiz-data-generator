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
        childColumns = arrayOf("quiz"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Category(val name: String, var icon: String? = null, val quiz: Long) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
