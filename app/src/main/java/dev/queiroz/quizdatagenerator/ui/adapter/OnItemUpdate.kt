package dev.queiroz.quizdatagenerator.ui.adapter

import dev.queiroz.quizdatagenerator.data.entity.Answer

interface OnItemUpdate {
    fun onUpdated(answer: Answer)
}