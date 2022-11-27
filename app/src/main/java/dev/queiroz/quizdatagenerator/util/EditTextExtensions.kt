package dev.queiroz.quizdatagenerator.util

import android.widget.EditText

fun EditText.isTextNullOrBlank(): Boolean = this.text.isNullOrBlank()