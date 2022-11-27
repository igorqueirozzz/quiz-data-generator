package dev.queiroz.quizdatagenerator.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.queiroz.quizdatagenerator.model.Category
import javax.inject.Inject

class NewQuizViewModel @Inject constructor() : ViewModel() {
    private val _stepper = MutableLiveData(0)
    val stepper: LiveData<Int> = _stepper

    private val _categories = MutableLiveData(mutableListOf<Category>())
    val categories: LiveData<MutableList<Category>> = _categories

    fun nextStep(){
        if (_stepper.value == 0) _stepper.value = (_stepper.value!! + 1)
    }

    fun backStep(){
         _stepper.value = (_stepper.value!! - 1)
        if(_stepper.value!! < 0) _stepper.value = 0
    }

    fun addCategory(category: Category){
        _categories.value?.add(category)
    }

}