package com.example.lab_3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataModel : ViewModel() {
    val colorBackground: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}