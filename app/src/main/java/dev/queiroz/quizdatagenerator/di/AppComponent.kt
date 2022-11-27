package dev.queiroz.quizdatagenerator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.queiroz.quizdatagenerator.activity.NewQuizActivity
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(newQuizActivity: NewQuizActivity)
}