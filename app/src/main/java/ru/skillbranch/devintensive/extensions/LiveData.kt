package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(defaultValue: T? = null) = MutableLiveData<T>().apply {
    if (defaultValue != null) this.value = defaultValue
}