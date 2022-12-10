package dev.queiroz.quizdatagenerator.model

data class Question(var id: Int?, val category: Category, val questionText: String, val source: String, val answers: List<Answer>)
