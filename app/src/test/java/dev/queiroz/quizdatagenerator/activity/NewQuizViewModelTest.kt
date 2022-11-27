package dev.queiroz.quizdatagenerator.activity

import dev.queiroz.quizdatagenerator.model.Category
import org.junit.Assert.*


import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewQuizViewModelTest {

    private val viewModel = NewQuizViewModel()

    @Test
    fun `When add a new category it must be added on categories list`() {
        val category = Category(description = "Test", icon = "Test")

        viewModel.addCategory(category)

        assertEquals(viewModel.categories.value!![0], category)
    }
}