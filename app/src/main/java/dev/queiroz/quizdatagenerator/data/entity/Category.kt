package dev.queiroz.quizdatagenerator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Category(val description: String, var icon: String? = null){
    @PrimaryKey(autoGenerate = true) val id: Long = 0
}
