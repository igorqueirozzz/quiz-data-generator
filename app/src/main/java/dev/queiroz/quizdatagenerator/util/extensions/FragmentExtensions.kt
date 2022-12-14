package dev.queiroz.quizdatagenerator.util.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun <T : ViewModel> Fragment.obtainViewModel(
    owner: ViewModelStoreOwner,
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
) = ViewModelProvider(owner, viewModelFactory)[viewModelClass]