package dev.queiroz.quizdatagenerator.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.queiroz.quizdatagenerator.data.entity.AnswerList

class GsonTypeConverter {
    @TypeConverter
    fun fromAnswerListToJson(answerList: AnswerList): String{
        return Gson().toJson(answerList)
    }
    @TypeConverter
    fun fromJsonToAnswerList(json: String): AnswerList{
        return Gson().fromJson(json, AnswerList::class.java)
    }
}