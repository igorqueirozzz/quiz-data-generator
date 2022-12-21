package dev.queiroz.quizdatagenerator.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(val description: String, var isCorrect: Boolean): Parcelable
