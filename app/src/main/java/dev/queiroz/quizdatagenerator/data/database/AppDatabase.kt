package dev.queiroz.quizdatagenerator.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.queiroz.quizdatagenerator.data.converter.GsonTypeConverter
import dev.queiroz.quizdatagenerator.data.dao.CategoryDAO
import dev.queiroz.quizdatagenerator.data.dao.QuestionDAO
import dev.queiroz.quizdatagenerator.data.dao.QuizDAO
import dev.queiroz.quizdatagenerator.data.entity.Category
import dev.queiroz.quizdatagenerator.data.entity.Question
import dev.queiroz.quizdatagenerator.data.entity.Quiz

@Database(entities = [Quiz::class, Category::class, Question::class], version = 1, exportSchema = false)
@TypeConverters(value = [GsonTypeConverter::class])
abstract class AppDatabase : RoomDatabase(){
    abstract fun questionDAO(): QuestionDAO
    abstract fun quizDAO(): QuizDAO
    abstract fun categoryDAO(): CategoryDAO

    companion object{
         const val DB_NAME = "app_database"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}