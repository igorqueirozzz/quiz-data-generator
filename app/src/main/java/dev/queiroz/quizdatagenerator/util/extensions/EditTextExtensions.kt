package dev.queiroz.quizdatagenerator.util.extensions

import android.widget.EditText

fun EditText.isTextNullOrBlank(): Boolean = this.text.isNullOrBlank()