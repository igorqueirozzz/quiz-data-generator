package dev.queiroz.quizdatagenerator.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.queiroz.quizdatagenerator.data.dao.QuestionDAO
import dev.queiroz.quizdatagenerator.data.dao.QuizDAO
import dev.queiroz.quizdatagenerator.data.database.AppDatabase
import dev.queiroz.quizdatagenerator.data.repository.QuestionRepository
import dev.queiroz.quizdatagenerator.data.repository.QuestionRepositoryDatabase
import dev.queiroz.quizdatagenerator.data.repository.QuizRepository
import dev.queiroz.quizdatagenerator.data.repository.QuizRepositoryFromDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
            return Room
                .databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME)
                .build()
        }
        @Singleton
        @Provides
        fun provideQuestionDao(@ApplicationContext context: Context): QuestionDAO {
            return AppDatabase.getDatabase(context).questionDAO()
        }

        @Singleton
        @Provides
        fun provideQuizDAO(@ApplicationContext context: Context): QuizDAO {
            return AppDatabase.getDatabase(context).quizDAO()
        }
    }


    @Binds
    @Singleton
    abstract fun bindQuestionRepository(questionRepositoryDatabase: QuestionRepositoryDatabase): QuestionRepository
    @Binds
    @Singleton
    abstract fun bindQuizRepository(quizRepositoryDatabase: QuizRepositoryFromDatabase): QuizRepository

}