package dev.queiroz.quizdatagenerator.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerList(var answers: List<Answer>): Parcelable